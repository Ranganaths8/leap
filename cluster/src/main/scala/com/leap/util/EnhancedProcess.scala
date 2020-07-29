package com.leap.util

import scala.sys.process.Process

trait ConsoleOutput {
  def output: String
  def error: String
}

/** Extends Process by providing a additional logger: ConsoleOutput interface. */
class EnhancedProcess(process: Process, _logger: ConsoleOutput) extends Process {
  def exitValue(): scala.Int = process.exitValue()
  def destroy(): scala.Unit = process.destroy()
  def isAlive(): Boolean = process.isAlive()
  def logger: ConsoleOutput = _logger
}
