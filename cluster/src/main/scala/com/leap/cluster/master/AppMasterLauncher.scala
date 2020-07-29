package com.leap.cluster.master

import akka.actor.typed.ActorRef
import com.leap.cluster.master.Master.MasterCmd
import com.leap.cluster.{AppDescription, AppJar}

class AppMasterLauncher {




}



trait AppMasterLauncherFactory {
  def props(appId: Int, executorId: Int, app: AppDescription, jar: Option[AppJar],
            username: String, master: ActorRef[MasterCmd], client: Option[ActorRef[]])

}
