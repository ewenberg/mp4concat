package com.eaw.moviemerge

import org.scalatest.FunSuite

/**
  * Created by erikwenberg on 12/25/17.
  */
class TrackTest extends FunSuite {

  test("parse") {
    val result = Track.parse("01:02,Track 1")
    assert(result.duration === SimpleTime(0, 1, 2))
  }
}
