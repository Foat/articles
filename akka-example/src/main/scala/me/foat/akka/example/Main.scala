package me.foat.akka.example

import akka.actor.{ActorSystem, PoisonPill, Props}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps

/**
  * @author Foat Akhmadeev
  *         27/12/15
  */
object Main extends App {
  val system = ActorSystem()
  val indexer = system.actorOf(Props(new Store))

  val sender1 = system.actorOf(Props(new Sender(indexer, 1)))
  val sender2 = system.actorOf(Props(new Sender(indexer, 2)))
  val sender3 = system.actorOf(Props(new Sender(indexer, 3)))

  sender1 ! "start"
  sender2 ! "start"
  sender3 ! "start"

  system.scheduler.scheduleOnce(2 seconds)({indexer ! PoisonPill; system.terminate})
}
