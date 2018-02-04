package com.eaw.moviemerge

import java.io.{BufferedWriter, File, FileInputStream, FileWriter}

import org.apache.commons.cli._

import scala.util.matching.Regex
import scala.io.Source

/**
  * Created by erikwenberg on 12/26/17.
  *
  **/
object CreateIndexFile extends App with CommandHelpers {

  def buildOptions() = {
    val options = new Options()
    val commandGroup = new OptionGroup()

    val inputDir = Option.builder
      .argName("inputDir")
      .desc("Directory location of the media files")
      .longOpt("inputDir")
      .required()
      .hasArg()
      .build()
    val outputFile = Option.builder
      .argName("outputFile")
      .desc("File name for the new template")
      .longOpt("outputFile")
      .hasArg()
      .build()
    val extensions = Option.builder
      .argName("extensions")
      .desc("List of extensions for files in the input directory (mp4, avi, etc)")
      .longOpt("extensions")
      .hasArgs()
      .required()
      .build()

    options.addOption(inputDir)
    options.addOption(extensions)
    options.addOption(outputFile)

    options
  }


  def createChapterList(dir: File, extensions: Array[String]) = {
    val good = dir.listFiles.filter(_.isFile).toList.filter { file =>
      extensions.exists(file.getName.endsWith(_))
    }

    //good ++ these.filter(_.isDirectory).flatMap(recursiveListFiles(_,r))
    val lines = good.map(g => s"${FileUtils.escapeChars(g.toString)}|${FileUtils.stripPath(g.toString)}")
    lines.foreach(println)
    lines
  }

  val options = buildOptions()

  val parser = new DefaultParser()

  try {
    val cmdLine = parser.parse(options, args)
    val inputDirOpt = cmdLine.getOptionValue("inputDir")
    val extensionsOpt = cmdLine.getOptionValues("extensions")
    val outputFileOpt = cmdLine.getOptionValue("outputFile", "chapters.txt")
    val inputDir = checkFile("Input Directory", inputDirOpt)

    val lines = createChapterList(inputDir, extensionsOpt)
    val file = new File(outputFileOpt)
    val bw = new BufferedWriter(new FileWriter(file))
    lines.foreach(line => {
      bw.write(line + "\n")
    })
    bw.close()

  } catch {
    case t : Throwable => printUsage("CreateIndexFile", options)
      System.exit(1)
  }



}
