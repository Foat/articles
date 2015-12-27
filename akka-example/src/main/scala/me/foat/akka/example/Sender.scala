package me.foat.akka.example

import akka.actor.{Actor, ActorRef, PoisonPill}

/**
  * @author Foat Akhmadeev
  *         27/12/15
  */
class Sender(indexer: ActorRef, id: Int) extends Actor {
  var count = 0

  def receive: Receive = {
    case "start" =>
      println(s"$id sends $count to the indexer")
      indexer ! s"$id => $count"
      count += 1
      if (count < 5)
        self ! "start"
      else self ! PoisonPill
  }
}
