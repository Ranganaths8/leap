package com.leap.metrics

import com.codahale.metrics.{Counter => CodaHaleCounter}

class Counter(val name: String, counter: CodaHaleCounter, sampleRate: Int = 1) {
  private var sampleCount = 0L
  private var toBeIncremented = 0L

  def inc() {
    inc(1)
  }

  def inc(n: Long) {
    toBeIncremented += n
    sampleCount += 1
    if (null != counter && sampleCount % sampleRate == 0) {
      counter.inc(toBeIncremented)
      toBeIncremented = 0
    }
  }
}