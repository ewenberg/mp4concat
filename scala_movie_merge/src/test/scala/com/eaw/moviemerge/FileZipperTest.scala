package com.eaw.moviemerge

import org.scalatest.FunSuite

import scala.io.Source

/**
  * Created by erikwenberg on 12/26/17.
  */
class FileZipperTest extends FunSuite {

  test("zip") {
    val zip = FileZipper.run(this.getClass.getResourceAsStream("/filenames.txt"),
      this.getClass.getResourceAsStream("/tracknames.txt"))
    assert(zip.length === 3)
    assert(zip(0) === "File1.txt|Track1")
    assert(zip(1) === "File2.txt|Track2")
    assert(zip(2) === "File3.txt|Track3")
  }
}
