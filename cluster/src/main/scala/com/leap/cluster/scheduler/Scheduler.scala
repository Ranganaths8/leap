package com.leap.cluster.scheduler

import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import com.leap.cluster.scheduler.Scheduler.SchedulerCmd

private[cluster] abstract class Scheduler(context:ActorContext[SchedulerCmd]) extends AbstractBehavior[SchedulerCmd](context ) {






}


object Scheduler{

  trait SchedulerCmd

}
