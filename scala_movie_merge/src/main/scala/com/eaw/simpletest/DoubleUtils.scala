package com.eaw.simpletest

/**
  * Created by erikwenberg on 12/25/17.
  */
object DoubleUtils {
  implicit class DoubleComparison(d: Double) {
    def ~=(other: Double, precision: Double = 0.0000000000000001): Boolean = {
      if ((d - other).abs < precision) true else false
    }

    def truncateAt(decimalPlaces: Int): Double = {
      val s = math.pow(10, decimalPlaces)
      math.floor(d*s)/s
    }

    def roundAt(p: Int): Double = {
      val s = math pow (10, p); (math round d * s) / s
    }
  }
}
