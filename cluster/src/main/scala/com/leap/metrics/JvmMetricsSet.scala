package com.leap.metrics

import java.util

import com.codahale.metrics.{Metric, MetricSet}
import com.codahale.metrics.jvm.{MemoryUsageGaugeSet, ThreadStatesGaugeSet}
import scala.jdk.CollectionConverters._

class JvmMetricsSet(name: String) extends MetricSet {

  override def getMetrics: util.Map[String, Metric] = {
    val memoryMetrics = new MemoryUsageGaugeSet().getMetrics.asScala
    val threadMetrics = new ThreadStatesGaugeSet().getMetrics.asScala
    Map(
      s"$name:memory.total.used" -> memoryMetrics("total.used"),
      s"$name:memory.total.committed" -> memoryMetrics("total.committed"),
      s"$name:memory.total.max" -> memoryMetrics("total.max"),
      s"$name:memory.heap.used" -> memoryMetrics("heap.used"),
      s"$name:memory.heap.committed" -> memoryMetrics("heap.committed"),
      s"$name:memory.heap.max" -> memoryMetrics("heap.max"),
      s"$name:thread.count" -> threadMetrics("count"),
      s"$name:thread.daemon.count" -> threadMetrics("daemon.count")
    ).asJava
  }
}
