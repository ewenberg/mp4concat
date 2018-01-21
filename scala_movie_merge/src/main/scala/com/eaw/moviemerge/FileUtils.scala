package com.eaw.moviemerge

import java.io.InputStream

import scala.io.Source

/**
  * Created by erikwenberg on 12/25/17.
  */
object FileUtils {
  def stripPath(s: String): Any = {
    val parts = s.split("/")
    parts(parts.length - 1)
  }


  def zip(fileNameList: List[String], trackNameList: List[String]): List[String] = {
    val zipped = fileNameList.zip(trackNameList).map(p => {
      s"${p._1}|${p._2}"
    })
    zipped
  }

  def zip(fileNameStream: InputStream, trackNameStream: InputStream): List[String] = {
    zip(Source.fromInputStream(fileNameStream).getLines().toList,
      Source.fromInputStream(trackNameStream).getLines.toList)
  }

}
