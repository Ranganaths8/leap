package com.leap.metrics

import com.codahale.metrics.{Histogram => CodaHaleHistogram}

class Histogram(val name: String, histogram: CodaHaleHistogram, sampleRate: Int = 1) {
  private var sampleCount = 0L

  def update(value: Long) {
    sampleCount += 1
    if (null != histogram && sampleCount % sampleRate == 0) {
      histogram.update(value)
    }
  }

  def getMean(): Double = {
    histogram.getSnapshot.getMean
  }

  def getStdDev(): Double = {
    histogram.getSnapshot.getStdDev
  }
}