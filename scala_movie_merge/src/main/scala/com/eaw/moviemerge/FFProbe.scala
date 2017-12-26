package com.eaw.moviemerge

import scala.sys.process._

/**
  * Created by erikwenberg on 12/25/17.
  */
class FFProbe {

}

object FFProbe {
  val durationRegex = """duration=(.*$)""".r

  def duration(fileName: String) = {
    val result = Process(s"ffprobe -show_format ${fileName}").lineStream
    var durationString = "0"
    result.foreach(line => {
      line match {
        case durationRegex(foundDuration) => durationString = foundDuration
        case _ =>
      }
    })
    SimpleTime.fromSeconds(durationString)
  }
}