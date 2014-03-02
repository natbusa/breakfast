package com.natalinobusa.breakfast

import akka.actor.{ ActorSystem, Actor, Props }
import akka.io.IO
import spray.can.Http

object Boot extends App {
  implicit val system = ActorSystem()
  
  // create and start our service actor
  val service = system.actorOf(Props[BreakfastApiService], "breakfast-api-service")
  
  // start a new HTTP server with our service actor as the handler
  IO(Http) ! Http.Bind(service, "localhost", port = 8888)

}