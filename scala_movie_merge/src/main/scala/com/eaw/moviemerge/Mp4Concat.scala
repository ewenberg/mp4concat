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
    val outputFile = Option.builder
      .argName("outputFile")
      .desc("Name for the new file that concatenates the input files")
      .longOpt("outputFile")
      .hasArg()
      .build()

    options.addOption(indexFile)
    options.addOption(outputFile)

    options
  }

  def showCommand() = {

  }

  val options = buildOptions()

  val parser = new DefaultParser()

  try {
    val cmdLine = parser.parse(options, args)
    val indexFileOpt = cmdLine.getOptionValue("indexFile")
    val outputFileOpt = cmdLine.getOptionValue("outputFile", "movie.mp4")
    val inputDir = checkFile("Index File", indexFileOpt)

    var runningDuration = SimpleTime.fromSeconds("0")
    val chapterDurations = new ListBuffer[SimpleTime]()
    val chapterStartTimes = new ListBuffer[SimpleTime]()

    val fileContents = Source.fromFile(indexFileOpt).getLines().toArray
    fileContents.foreach(line => {
      val parts = line.split("\\|")
      val fileName = parts(0)
      val duration = FFProbe.duration(fileName)
      chapterDurations += duration
      chapterStartTimes += runningDuration
      runningDuration = runningDuration + duration
    })

    //println(runningDuration)
    //println(chapterDurations)
    //println(chapterStartTimes)
  } catch {
    case t : Throwable => printUsage("Mp4Concat", options)
      System.exit(1)
  }

}
