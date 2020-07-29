package com.leap.cluster.master

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.cluster.ddata.LWWMap
import akka.cluster.ddata.LWWMapKey
import akka.cluster.ddata.SelfUniqueAddress
import akka.cluster.ddata.typed.scaladsl.DistributedData
import akka.cluster.ddata.Replicator.{NotFound, ReadLocal,WriteLocal}
import akka.cluster.ddata.typed.scaladsl.Replicator.{GetResponse, Get,GetSuccess, Update, UpdateResponse}
import com.leap.util.LogUtil
import org.slf4j.Logger


object InMemoryKVStore {
  sealed trait KVCommand
  final case class PutInKV(key: String, value: String) extends KVCommand
  final case class GetFromKV(key: String, replyTo: ActorRef[Cached]) extends KVCommand
  final case class Cached(key: String, value: Option[String])
  final case class deleteKV(key: String) extends KVCommand
  private sealed trait InternalCommand extends KVCommand
  private case class InternalGetResponse(key: String, replyTo: ActorRef[Cached], rsp: GetResponse[LWWMap[String, String]])
    extends InternalCommand
  private case class InternalUpdateResponse(rsp: UpdateResponse[LWWMap[String, String]]) extends InternalCommand
  private val LOG: Logger = LogUtil.getLogger(getClass)

  def apply(): Behavior[KVCommand] = Behaviors.setup { context =>
    DistributedData.withReplicatorMessageAdapter[KVCommand, LWWMap[String, String]] { replicator =>
      implicit val node: SelfUniqueAddress = DistributedData(context.system).selfUniqueAddress

      def dataKey(entryKey: String): LWWMapKey[String, String] =
        LWWMapKey("cache-" + math.abs(entryKey.hashCode % 100))

      Behaviors.receiveMessage[KVCommand] {
        case PutInKV(key, value) =>
          LOG.info("PUTInKV")
          replicator.askUpdate(
            askReplyTo => Update(dataKey(key), LWWMap.empty[String, String], WriteLocal, askReplyTo)(_ :+ (key -> value)),
            InternalUpdateResponse.apply)

          Behaviors.same

        case deleteKV(key) =>
          LOG.info("deleteKV")
          replicator.askUpdate(
            askReplyTo => Update(dataKey(key), LWWMap.empty[String, String], WriteLocal, askReplyTo)(_.remove(node, key)),
            InternalUpdateResponse.apply)

          Behaviors.same

        case GetFromKV(key, replyTo) =>
          LOG.info("getFromKV")
          replicator.askGet(
            askReplyTo => Get(dataKey(key), ReadLocal, askReplyTo),
            rsp => InternalGetResponse(key, replyTo, rsp))
          Behaviors.same

        case InternalGetResponse(key, replyTo, g @ GetSuccess(_)) =>
          LOG.info("InternalGetResponse")
          replyTo ! Cached(key, g.dataValue.get(key))
          Behaviors.same

        case InternalGetResponse(key, replyTo, _: NotFound[_]) =>
          LOG.info("InternalGETResponse - NotFound")
          replyTo ! Cached(key, None)
          Behaviors.same

        case _: InternalGetResponse    => Behaviors.same // ok
        case _: InternalUpdateResponse => Behaviors.same // ok
      }
    }
  }
}




