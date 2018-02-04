package com.eaw.moviemerge

import java.net.URLEncoder

import org.scalatest.FunSuite

/**
  * Created by erikwenberg on 12/25/17.
  */
class FFProbeTest extends FunSuite {

  test("duration") {
    assert(SimpleTime.fromSeconds("261.04") === FFProbe.duration("./test.mp4"))
  }

  test("filenames with spaces") {
    assert(SimpleTime.fromSeconds("261.04") === FFProbe.duration("./test 2.mp4"))
  }
}
