package com.leap.cluster.master

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior, Terminated}
import com.leap.cluster.master.InMemoryKVStore.KVCommand


object AppManager {

     trait AppMgrCmd

     //,launcher: AppMasterLauncherFactory removed as parameter for time being @todo
     def apply(kvService: ActorRef[KVCommand]): Behavior[AppMgrCmd] =
          Behaviors
            .receive[AppMgrCmd] { (context, message) =>
                 message match {

                      case _ => Behaviors.same
                 }
            }.receiveSignal{
               case (context, Terminated(ref)) =>
                    context.log.info("AppManager stopped: {}", ref.path.name)
                    Behaviors.same
          }
}