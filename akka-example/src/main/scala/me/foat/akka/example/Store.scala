package me.foat.akka.example

import akka.actor.Actor

/**
  * @author Foat Akhmadeev
  *         27/12/15
  */
class Store extends Actor {
  var list = List.empty[String]

  def receive: Receive = {
    case msg: String =>
      list = msg :: list
  }

  @throws[Exception](classOf[Exception])
  override def postStop(): Unit = {
    super.postStop()
    println(list.reverse)
  }
}