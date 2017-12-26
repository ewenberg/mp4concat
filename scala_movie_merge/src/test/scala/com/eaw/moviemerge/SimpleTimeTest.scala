package com.eaw.moviemerge

import org.scalatest.FunSuite


/**
  * Created by erikwenberg on 12/24/17.
  */
class SimpleTimeTest extends FunSuite {

  test("test") {
    assert("00:00:00.000" === SimpleTime(0, 0, 0).toString)
  }

  test("parses strings with hours") {
    val testSubject = SimpleTime.parse("01:02:15")
    assert(1 === testSubject.hours)
    assert(2 === testSubject.minutes)
    assert(15 === testSubject.seconds)
  }

  test("parses strings with only minutes and seconds") {
    val testSubject = SimpleTime.parse("04:16")
    assert(0 === testSubject.hours)
    assert(4 === testSubject.minutes)
    assert(16 === testSubject.seconds)
  }

  test("equals") {
    val st123 = SimpleTime(1, 2, 3, 4)
    val alsoSt123 = SimpleTime(1, 2, 3, 4)

    assert(st123 === alsoSt123)

    val st124 = SimpleTime(1,2,4,5)
    assert(st123 !== st124)

  }

  test("adds seconds") {
    val st1 = SimpleTime(0,0,50)
    val st2 = SimpleTime(0,0,11)
    val st3 = SimpleTime(0,1,1)
    assert((st1 + st2) === st3)
  }

  test("adds minutes") {
    val st1 = SimpleTime(0, 30, 0)
    val st2 = SimpleTime(0, 10, 0)
    val st3 = SimpleTime(0, 40, 0)

    assert((st1 + st2) === st3)
  }

  test("adds milliseconds") {
    val st1 = SimpleTime(0, 0, 1, 2)
    val st2 = SimpleTime(0, 0, 2, 3)
    val st3 = SimpleTime(0, 0, 3, 5)

    assert((st1 + st2) === st3)
  }

  test("normalizes seconds") {
    assert(SimpleTime(0, 0, 61) === SimpleTime(0, 1, 1))
  }

  test("normalizes milliseconds") {
    assert(SimpleTime(0, 0, 0, 1001) === SimpleTime(0, 0, 1, 1))
  }

  test("normalizes minutes") {
    assert(SimpleTime(0, 85, 10) === SimpleTime(1, 25, 10))
  }

  test("from seconds") {
    assert(SimpleTime.fromSeconds(60) === SimpleTime(0, 1, 0))
    assert(SimpleTime.fromSeconds(120) === SimpleTime(0, 2, 0))
    assert(SimpleTime.fromSeconds(3601) === SimpleTime(1, 0, 1))
    assert(SimpleTime.fromSeconds(3661) === SimpleTime(1, 1, 1))

    assert(SimpleTime.fromSeconds(45.351) === SimpleTime(0, 0, 45, 351))
    assert(SimpleTime.fromSeconds("23.129") === SimpleTime(0, 0, 23, 129))
  }
}
