package com.natalinobusa.breakfast

// the service, actors and paths
import spray.routing.HttpServiceActor
import spray.routing.directives.PathDirectives._

// our breakfast elements
import elements._

// marshalling breakfast to json
import spray.httpx.SprayJsonSupport.sprayJsonMarshaller
import elements.JsonImplicits._

// Routing embedded in the actor
class BreakfastApiService extends HttpServiceActor {

  //curl -vv -H "Content-Type: application/json" localhost:8888/api/v1/breakfast?eggs=2&strips=4&slices=1&juices=1&coffee=2

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
                SideDish(
                  ToastedBread(breadslices)),
                Drinks(
                  OrangeJuice(orangejuices),
                  HotCoffee(coffeemugs)))
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