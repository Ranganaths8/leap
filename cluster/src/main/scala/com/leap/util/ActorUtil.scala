package com.leap.util

import org.slf4j.Logger
import akka.actor.typed.ActorSystem
import akka.actor._

object ActorUtil {

  private val LOG: Logger = LogUtil.getLogger(getClass)

  def getSystemAddress[T](system: ActorSystem[T]): Address = {
    system.asInstanceOf[ExtendedActorSystem].provider.getDefaultAddress
  }

 /* def getFullPath[T](system: ActorSystem[T], actorRef: ActorRef): String = {
    if (actorRef != ActorRef.noSender) {
      getFullPath(system, actorRef.path)
    } else {
      ""
    }
  }*/

}
