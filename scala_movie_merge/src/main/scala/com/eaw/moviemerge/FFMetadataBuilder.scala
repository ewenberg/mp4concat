package com.eaw.moviemerge

import scala.collection.mutable.ListBuffer

/**
  * Created by erikwenberg on 1/28/18.
  */
class FFMetadataBuilder {
  var header = new Header("", "")
  val chapters = new ListBuffer[Chapter]()

  def build() = {
    header.build() + "\n" + chapters.map(_.build()).mkString("\n")
  }

  def withTitle(title: String) = {
    header.title = title
    this
  }

  def withArtist(artist: String) = {
    header.artist = artist
    this
  }

  def withHeader(title: String, artist: String) = {
    // can only have one header
    header = new Header(title, artist)
    this
  }

  def addChapter(startMillis: Integer, endMillis: Integer, title: String) = {
    chapters += new Chapter(startMillis, endMillis, title)
    this
  }

  class Header(var title: String, var artist: String) {
    val headerTag = ";FFMETADATA1"

    def build() = {
      val b = new ListBuffer[String]()
      b += headerTag
      if (title.length > 0) {
        b += s"title=${title}"
      }
      if (artist.length > 0) {
        b += s"artist=${artist}"
      }

      b.mkString("\n")
    }
  }

  class Chapter(val startMillis: Integer, val endMillis: Integer, val title: String) {
    val chapterTag = "[CHAPTER]"
    val titleTag = s"TITLE=${title}"
    val startTag = s"START=${startMillis}"
    val endTag = s"END=${endMillis}"
    val timeBaseTag = s"TIMEBASE=1/1000"

    def build() = {
      s"${chapterTag}\n${timeBaseTag}\n${startTag}\n${endTag}\n${titleTag}"
    }
  }
}
