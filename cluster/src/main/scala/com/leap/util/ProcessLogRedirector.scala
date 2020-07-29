

package com.leap.util

import java.io.{Closeable, Flushable}

import org.slf4j.LoggerFactory

import scala.sys.process.ProcessLogger

/** Redirect the console output to parent process */
class ProcessLogRedirector extends ProcessLogger with Closeable with Flushable with ConsoleOutput {
  private val LOG = LoggerFactory.getLogger("redirect")

  // We only capture the first 1K chars
  private final val LENGTH = 1000
  private var _error: String = ""
  private var _output: String = ""

  def error: String = _error
  def output: String = _output

  def out(s: => String): Unit = {
    if (_output.length <= LENGTH) {
      _output += "\n" + s
    }
    LOG.info(s)
  }
  def err(s: => String): Unit = {
    if (_error.length <= LENGTH) {
      _error += "\n" + s
    }
    LOG.error(s)
  }
  def buffer[T](f: => T): T = f
  def close(): Unit = Unit
  def flush(): Unit = Unit
}