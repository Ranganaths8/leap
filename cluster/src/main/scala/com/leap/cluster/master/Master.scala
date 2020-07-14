package com.leap.cluster.master

import com.leap.util.LogUtil
import com.typesafe.config.Config
import org.slf4j.Logger


private[cluster] class Master {



  private val LOG: Logger = LogUtil.getLogger(getClass)

  private val systemConfig: Config = context.system.settings.config



}



