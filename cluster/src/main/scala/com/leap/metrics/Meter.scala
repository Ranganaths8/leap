/*
 * Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.leap.metrics

import com.codahale.metrics.{Meter => CodaHaleMeter}

class Meter(val name: String, meter: CodaHaleMeter, sampleRate: Int = 1) {
  private var sampleCount = 0L
  private var toBeMarked = 0L

  def mark() {
    meter.mark(1)
  }

  def mark(n: Long) {
    toBeMarked += n
    sampleCount += 1
    if (null != meter && sampleCount % sampleRate == 0) {
      meter.mark(toBeMarked)
      toBeMarked = 0
    }
  }

  def getOneMinuteRate(): Double = {
    meter.getOneMinuteRate
  }
}