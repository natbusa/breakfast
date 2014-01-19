package com.natalinobusa.breakfast

import elements._

import akka.actor.{ Actor }
import spray.routing.HttpService
import spray.routing.directives.PathDirectives._

import spray.json.DefaultJsonProtocol
import spray.httpx.SprayJsonSupport.sprayJsonMarshaller
import spray.httpx.SprayJsonSupport.sprayJsonUnmarshaller

object JsonImplicits extends DefaultJsonProtocol {
  implicit val impCrispyBacon = jsonFormat1(CrispyBacon)
  implicit val impFriedEggs = jsonFormat1(FriedEggs)
  implicit val impMainDish = jsonFormat2(MainDish)

  implicit val impToastedBread = jsonFormat1(ToastedBread)

  implicit val impHotCoffee = jsonFormat1(HotCoffee)
  implicit val impOrangeJuice = jsonFormat1(OrangeJuice)

  implicit val impBreakfast = jsonFormat4(Breakfast)
}

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class BreakfastApiService extends Actor with HttpService {

  import JsonImplicits._

  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  //curl -vv -H "Content-Type: application/json" localhost:8888/api/v1/breakfast?eggs=2&strips=4&slices=1&juices=1&coffee=2
  //curl -vv -X POST -H "Content-Type: application/json" -d '{"fname":"a", "v":3, "f":1.23}' localhost:8888/api/v1/s/123
  //curl -vv -H "Content-Type: application/json" localhost:8888/api/v1/s/123

  val serviceRoute = {

    pathPrefix("api" / "v1" / "breakfast") {
      get {
        parameters('eggs.as[Int], 'strips.as[Int], 'slices.as[Int], 'juices.as[Int], 'coffee.as[Int]) {
          (friedeggs, baconstrips, breadslices, orangejuices, coffeemugs) =>

            complete {
              Breakfast(
                MainDish(
                  FriedEggs(friedeggs),
                  CrispyBacon(baconstrips)),
                ToastedBread(breadslices),
                OrangeJuice(orangejuices),
                HotCoffee(coffeemugs))
            }
        }
      }
    }
  }

  // this actor only runs our route, but you could add
  // other things here, like request stream processing,
  // timeout handling or alternative handler registration
  def receive = runRoute(serviceRoute)

}