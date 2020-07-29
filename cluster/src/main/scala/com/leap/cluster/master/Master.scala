package com.leap.cluster.master

import akka.actor.typed.{ActorRef, Behavior, PostStop, Signal}
import akka.actor.typed.scaladsl.{AbstractBehavior, ActorContext, Behaviors}
import com.leap.cluster.master.AppManager.AppMgrCmd
import com.leap.cluster.master.Master.MasterCmd
import com.leap.cluster.scheduler.Scheduler.SchedulerCmd
import com.leap.cluster.worker.WorkerId
import com.leap.cluster.worker.worker.WorkerCmd
import com.leap.metrics.{JvmMetricsSet, Metrics}
import com.leap.util.{Constants, LogUtil}
import com.typesafe.config.Config
import org.slf4j.Logger

import scala.collection.immutable

//@todo need to implement Stash to make the state of actors durable.
class Master(context:ActorContext[MasterCmd]) extends AbstractBehavior[MasterCmd](context){

  private val LOG: Logger = LogUtil.getLogger(getClass)

  private val systemConfig: Config = context.system.settings.config

  private val kvService = context.spawn(InMemoryKVStore(), "kvService")

  private var appManager: ActorRef[AppMgrCmd] = context.spawn(AppManager(kvService),"appManager")

  val schedulerClass = Class.forName(
    systemConfig.getString(Constants.LEAP_SCHEDULING_SCHEDULER))

  private var scheduler: ActorRef[SchedulerCmd] = context.spawn(schedulerClass[SchedulerCmd],"Scheduler")

  private var workers = new immutable.HashMap[ActorRef[WorkerCmd], WorkerId]

  private val birth = System.currentTimeMillis()

  private var nextWorkerId = 0

  // Register jvm metrics
  Metrics(context.system).register(new JvmMetricsSet(s"master"))

  //LOG.info("master is started at " + ActorUtil.getFullPath(context.system, self.path) + "...")

 val jarStoreRootPath = systemConfig.getString(Constants.LEAP_APP_JAR_STORE_ROOT_PATH)

  //private val jarStore = context.actorOf(Props(classOf[JarStoreServer], jarStoreRootPath))

  //private val hostPort = HostPort(ActorUtil.getSystemAddress(context.system).hostPort)


  override def onMessage(msg: MasterCmd): Behavior[MasterCmd] ={
         Behaviors.same


  }

  override def onSignal: PartialFunction[Signal, Behavior[MasterCmd]] = {
    case PostStop => context
      context.log.info("Master Control Program stopped")
      Behaviors.same



  }
}


object  Master{

  def apply(groupId: String, deviceId: String): Behavior[MasterCmd] =
    Behaviors.setup{
      context => new Master(context)

    }

  trait MasterCmd
  final val MASTER_GROUP = "master_group"
  final val WORKER_ID = "next_worker_id"

  case class WorkerTerminated(workerId: WorkerId) extends MasterCmd

  case class MasterInfo(master: ActorRef[MasterCmd], startTime: Long = 0L) extends  MasterCmd

  case class MasterListUpdated(masters: List[Master]) extends  MasterCmd

  object MasterInfo {
    def empty: MasterInfo = MasterInfo(null)
  }
  case class SlotStatus(totalSlots: Int, availableSlots: Int) extends  MasterCmd

}



