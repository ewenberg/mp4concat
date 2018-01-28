package com.eaw.moviemerge

import com.eaw.moviemerge.DoubleUtils._

/**
  * Created by erikwenberg on 12/24/17.
  */
class SimpleTime(val hours: Int, val minutes: Int, val seconds: Int, val milliseconds: Int = 0) {

  override def toString() = {
    "%02d".format(hours) + ":" +
    "%02d".format(minutes) + ":" +
    "%02d".format(seconds) + "." +
    "%03d".format(milliseconds)
  }

  override def equals(obj: scala.Any): Boolean = {
    if (!obj.isInstanceOf[SimpleTime]) {
      false
    }
    val other = obj.asInstanceOf[SimpleTime]
    if (this.hours == other.hours &&
    this.minutes == other.minutes &&
    this.seconds == other.seconds &&
    this.milliseconds == other.milliseconds) {
      true
    } else {
      false
    }
  }

  def +(other: SimpleTime): SimpleTime = {
    SimpleTime(this.hours + other.hours, this.minutes + other.minutes, this.seconds + other.seconds, this.milliseconds + other.milliseconds)
  }

  def toMilliseconds() = {
    this.milliseconds +
    this.seconds * 1000 +
    this.minutes * 60 * 1000 +
    this.hours * 60 * 60 * 1000
  }
}

object SimpleTime {
  def apply(hours: Int, minutes: Int, seconds: Int, milliseconds: Int = 0) = {
    val initialSeconds = milliseconds / 1000
    val initialMinutes = seconds / 60
    val initialHours = minutes / 60

    new SimpleTime(hours + initialHours, (minutes%60) + initialMinutes, (seconds%60) + initialSeconds, milliseconds % 1000)
  }

  def fromSeconds(s: Double): SimpleTime = {
    val whole = s.toDouble.truncateAt(0)
    val milliseconds = (((s.toDouble - whole).roundAt(3) * 1000).truncateAt(0)).toInt

    val hours = (whole / 3600).toInt
    val minutes = ((whole - (3600 * hours)) / 60).toInt
    val seconds = (whole % 60).toInt
    SimpleTime(hours, minutes, seconds, milliseconds)
  }

  def fromSeconds(s: String): SimpleTime = {
    fromSeconds(s.toDouble)
  }

  def parse(s: String) = {
    def parts = s.split(":")
    if (parts.length == 3) {
      new SimpleTime(parts(0).toInt, parts(1).toInt, parts(2).toInt)
    } else if (parts.length == 2) {
      new SimpleTime(0, parts(0).toInt, parts(1).toInt)
    } else {
      throw new RuntimeException("Unrecognized format")
    }
  }
}
