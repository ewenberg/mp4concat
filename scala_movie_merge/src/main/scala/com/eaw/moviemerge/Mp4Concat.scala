package com.eaw.moviemerge

import java.io.{BufferedWriter, File, FileWriter}

import org.apache.commons.cli._

import scala.collection.mutable.ListBuffer
import scala.io.Source

/**
  * Created by erikwenberg on 1/23/18.
  */
object Mp4Concat extends App with CommandHelpers {

  def buildOptions() = {
    val options = new Options()
    val commandGroup = new OptionGroup()

    val indexFile = Option.builder
      .argName("indexFile")
      .desc("Name of the index file to use")
      .longOpt("indexFile")
      .required()
      .hasArg()
      .build()
    val title = Option.builder
      .argName("title")
      .desc("Name of the movie")
      .longOpt("title")
      .required()
      .hasArg()
      .build()
    val artist = Option.builder
      .argName("artist")
      .desc("Name of the artist")
      .longOpt("artist")
      .required()
      .hasArg()
      .build()
    val outputFile = Option.builder
      .argName("outputFile")
      .desc("Name for the new file that concatenates the input files")
      .longOpt("outputFile")
      .hasArg()
      .build()

    options.addOption(indexFile)
    options.addOption(outputFile)
    options.addOption(artist)
    options.addOption(title)

    options
  }

  val options = buildOptions()

  val parser = new DefaultParser()

  try {
    val cmdLine = parser.parse(options, args)
    val indexFileOpt = cmdLine.getOptionValue("indexFile")
    val outputFileOpt = cmdLine.getOptionValue("outputFile", "movie.mp4")
    val titleOpt = cmdLine.getOptionValue("title")
    val artistOpt = cmdLine.getOptionValue("artist")
    val inputDir = checkFile("Index File", indexFileOpt)

    var runningDuration = SimpleTime.fromSeconds("0")
    val chapterDurations = new ListBuffer[SimpleTime]()
    val chapterStartTimes = new ListBuffer[SimpleTime]()
    val fileNames = new ListBuffer[String]()
    val chapterNames = new ListBuffer[String]()

    val fileContents = Source.fromFile(indexFileOpt).getLines().toArray
    fileContents.foreach(line => {
      val parts = line.split("\\|")
      val fileName = parts(0)
      val duration = FFProbe.duration(fileName)

      fileNames += fileName
      chapterNames += parts(1)
      chapterDurations += duration
      chapterStartTimes += runningDuration
      runningDuration = runningDuration + duration
    })

    val inputFilePart = fileNames.map { s =>
      s.replaceAll(" ", "\\\\ ").replaceAll("\\(", "\\\\(").replaceAll("\\)", "\\\\)")
    }.mkString(" -i ", " -i ", "")
    //println(inputFilePart)

    val indexRange = 0 until fileNames.size
    val indexes = indexRange.map { i =>
      s"[${i}:0][${i}:1]"
    }.mkString("")
    val indexesPart = s"'${indexes}concat=n=${fileNames.size}:v=1:a=1[v][a]'"
    //println(indexesPart)

    val cmd = s"ffmpeg ${inputFilePart} -filter_complex ${indexesPart} -map [v] -map [a] -strict -2 ${outputFileOpt}"
    println("=====================================")
    println("  RUN THIS FFMPEG COMMAND")
    println("=====================================")
    println(cmd)

    println()
    println()

    val mdBuilder = new FFMetadataBuilder()
    mdBuilder.withHeader(titleOpt, artistOpt)
    chapterNames.zipWithIndex.foreach {
      case (chapterName, idx) => {
        val startMillis = chapterStartTimes(idx).toMilliseconds()
        val endMillis = startMillis + chapterDurations(idx).toMilliseconds()
        mdBuilder.addChapter(startMillis, endMillis, chapterName)
      }
    }

    println("===================================")
    println("  USE THIS METADATA")
    println("===================================")

    println(mdBuilder.build())

    println("===================================")
    println("  RUN THIS METADATA COMMAND")
    println("===================================")
    println("ffmpeg -i <metadata-file> -i <video-file> -codec copy <new-video-file>")


    println(s"Running duration: ${runningDuration.toString()}")
    
  } catch {
    case t : Throwable => {
      println(t)
      printUsage("Mp4Concat", options)
    }
      System.exit(1)
  }

}
