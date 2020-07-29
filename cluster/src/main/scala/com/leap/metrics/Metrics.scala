package com.leap.metrics

import akka.actor
import akka.actor.ExtensionIdProvider
import akka.actor.typed.{ActorRef, ActorSystem, Extension, ExtensionId}
import com.codahale.metrics.{MetricRegistry, MetricSet}
import com.leap.util.LogUtil
import org.slf4j.Logger
import com.leap.util.Constants._

import scala.jdk.CollectionConverters._

class Metrics(sampleRate:Int) extends Extension{

  val registry = new MetricRegistry()

  def meter(name: String): Meter = {
    new Meter(name, registry.meter(name), sampleRate)
  }

  def histogram(name: String): Histogram = {
    new Histogram(name, registry.histogram(name), sampleRate)
  }

  def histogram(name: String, sampleRate: Int): Histogram = {
    new Histogram(name, registry.histogram(name), sampleRate)
  }

  def counter(name: String): Counter = {
    new Counter(name, registry.counter(name), sampleRate)
  }

  def register(set: MetricSet): Unit = {
    val names = registry.getNames
    val metrics = set.getMetrics.asScala.view.filterKeys { key => !names.contains(key) }
    metrics.foreach { kv =>
      registry.register(kv._1, kv._2)
    }
  }
}

object Metrics extends ExtensionId[Metrics] with ExtensionIdProvider {

  val LOG: Logger = LogUtil.getLogger(getClass)

  sealed trait MetricType {
    def name: String
  }

  object MetricType {

    def unapply(obj: MetricType): Option[(Histogram, Counter, Meter, Timer, Gauge)] = {
      obj match {
        case x: Histogram => Some((x, null, null, null, null))
        case x: Counter => Some((null, x, null, null, null))
        case x: Meter => Some((null, null, x, null, null))
        case x: Timer => Some((null, null, null, x, null))
        case g: Gauge => Some((null, null, null, null, g))
      }
    }

    def apply(h: Histogram, c: Counter, m: Meter, t: Timer, g: Gauge): MetricType = {
      val result =
        if (h != null) h
        else if (c != null) c
        else if (m != null) m
        else if (t != null) t
        else if (g != null) g
        else null
      result
    }
  }

  case class Histogram (
                         name: String, mean: Double,
                         stddev: Double, median: Double,
                         p95: Double, p99: Double, p999: Double)
    extends MetricType

  case class Counter(name: String, value: Long) extends MetricType

  case class Meter(
                    name: String, count: Long, meanRate: Double,
                    m1: Double, rateUnit: String)
    extends MetricType

  case class Timer(
                    name: String, count: Long, min: Double, max: Double,
                    mean: Double, stddev: Double, median: Double,
                    p75: Double, p95: Double, p98: Double,
                    p99: Double, p999: Double, meanRate: Double,
                    m1: Double, m5: Double, m15: Double,
                    rateUnit: String, durationUnit: String)
    extends MetricType

  case class Gauge(name: String, value: Long) extends MetricType

  case object ReportMetrics

  case class DemandMoreMetrics(subscriber: ActorRef[_])

  override def createExtension(system: ActorSystem[_]): Metrics ={

    val metricsEnabled = system.settings.config.getBoolean(LEAP_METRIC_ENABLED)
    LOG.info(s"Metrics is enabled...,  $metricsEnabled")
    val sampleRate = system.settings.config.getInt(LEAP_METRIC_SAMPLE_RATE)
    if (metricsEnabled) {
      val meters = new Metrics(sampleRate)
      meters
    } else {
      new DummyMetrics
    }
  }

  class DummyMetrics extends Metrics(1) {
    override def register(set: MetricSet): Unit = Unit

    private val meter = new com.leap.metrics.Meter("", null) {
      override def mark(): Unit = Unit
      override def mark(n: Long): Unit = Unit
      override def getOneMinuteRate(): Double = 0
    }

    private val histogram = new com.leap.metrics.Histogram("", null) {
      override def update(value: Long): Unit = Unit
      override def getMean(): Double = 0
      override def getStdDev(): Double = 0
    }

    private val counter = new com.leap.metrics.Counter("", null) {
      override def inc(): Unit = Unit
      override def inc(n: Long): Unit = Unit
    }

    override def meter(name: String): com.leap.metrics.Meter = meter
    override def histogram(name: String): com.leap.metrics.Histogram = histogram
    override def counter(name: String): com.leap.metrics.Counter = counter
  }

  override def lookup(): actor.ExtensionId[_ <: actor.Extension] = Metrics
}