package com.eaw.moviemerge

import java.io.{File, FileInputStream}

import scala.io.Source

/**
  * Created by erikwenberg on 12/26/17.
  */
object MovieMerge extends App {
  if (args.length < 1) {
    println("Usage: <inputDir> <fileNamesFile (optional)> <chapterNamesFile (optional)>")
    System.exit(1)
  }
  
  args.foreach(println)
  val inputDir = args(0)
  val fileNamesFile = args(1)
  val chapterNamesFile = args(2)

  val index = FileZipper.run(new FileInputStream(new File(fileNamesFile)),
    new FileInputStream(new File(chapterNamesFile)))

  def load() = {
    var runningDuration = SimpleTime.fromSeconds("0")
    index.foreach(println)
  }


}
