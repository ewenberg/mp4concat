package com.eaw.moviemerge

/**
  * Created by erikwenberg on 12/25/17.
  */
class Track(val duration: SimpleTime, val title: String) {

}

object Track {
  def parse(s: String): Track = {
    val parts = s.split(",")
    new Track(SimpleTime.parse(parts(0)), parts(1))
  }
}
