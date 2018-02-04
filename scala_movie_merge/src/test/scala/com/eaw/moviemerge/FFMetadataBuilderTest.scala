package com.eaw.moviemerge

import org.scalatest.FunSuite

/**
  * Created by erikwenberg on 1/28/18.
  */
class FFMetadataBuilderTest extends FunSuite {
  val testSubject = new FFMetadataBuilder()

  test("header") {
    assert(testSubject.build().startsWith(";FFMETADATA1"))
  }

  test("title") {
    testSubject.withTitle("MyTitle")
    assert(testSubject.build().contains("title=MyTitle"))
  }

  test("artist") {
    testSubject.withArtist("MyArtist")
    assert(testSubject.build().contains("artist=MyArtist"))
  }

  test("withHeader") {
    val output = testSubject.withHeader("MyTitle", "MyArtist").build()
    assert(output.startsWith(";FFMETADATA1"))
    assert(output.contains("title=MyTitle"))
    assert(output.contains("artist=MyArtist"))
  }

  test("addChapter") {
    val output = testSubject.addChapter(1000, 2000, "ChapterTitle").build()
    println(output)
    assert(contains(output, "[CHAPTER]", 1))
    assert(contains(output, "START=1000", 1))
    assert(contains(output, "END=2000", 1))
    assert(contains(output, "TITLE=ChapterTitle", 1))
    assert(contains(output, "TIMEBASE=1/1000", 1))
  }

  def contains(s: String, word: String, count: Integer) = {
    s.split("\n").toList.count(_ == word) == count
  }
}
