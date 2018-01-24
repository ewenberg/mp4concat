package com.eaw.moviemerge

import java.io.File

import org.apache.commons.cli.{HelpFormatter, Options}

/**
  * Created by erikwenberg on 1/23/18.
  */
trait CommandHelpers {
  def printUsage(cmd: String, options: Options) = {
    val fmt = new HelpFormatter()
    fmt.printHelp(cmd, options)
  }

  def checkFile(paramName: String, location: String) = {
    println(paramName)
    println(location)
    val f = new File(location)
    if (!f.exists) {
      println(s"ERROR: ${paramName} '${location}' does not exist...")
      System.exit(1)
    }
    f
  }
}
