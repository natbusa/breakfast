Breakfast
=========

How to prepare breakfast in a reactive, asynchronous way in scala

Motivations
============

Preparing breakfast is an excellent exercise of practical real-life programming. It's a procedure which takes some input, namely your order, process the ingredients, and produce a result namely your desired breakfast.

Breakfast making has also some interesting properties. 

1. Can be (partially) parallelized, for instance by cooking eggs and toasting bread at the same time. 
2. It's constrained by the available resources, think of preparing two breakfasts with a single pan. 
3. A breakfast is a recipe. Which means that it's compositional by nature.   
   It should be easy to adjust the recipe in order to serve a different breakfast.
4. A breakfast can go wrong. For instance, your can burn your toast. What about error recovery and termination?

Preparing breakfast allows also to reason about the entities which take part of the process. 
Think of the ingredients (eggs, bacon), the cooked artifacts (scrambled eggs, fried bacon), the actors (the cook), and the utensils/functionals (e.g. the pan)

Also since this is a breakfast web API, this example allows you to reason about the concept of http routing, json (un-)marshalling, performance, monitoring. etc.
Enough motivations let's prepare some breakfast!

Specs
============
1. It's an API call

2. Inputs:   
	nr. of eggs, bacon stripes, toast slices, coffee mugs, and glasses of juices

3. Output:   
	Json of the assemble breakfast with main, side dishes and drinks

4. Timeout:   
	1 second to complete the breakfast.   
	Partially prepared breakfasts are not delivered.

5. Composable   
	Easy to maintain and modify the recipe.

6. Reactive and lazy   
	Make use of the resources as soon as possible, and only when necessary.

7. Scalable (In, Up, and Out)   
	Providing more resources, should (linearly) scale the system (increase throuput)   
	Providing more resources, should parallelize the breakfast making algorithm (descrease latency)

Reference Request/Response
============
Request:

	> GET /api/v1/breakfast?eggs=2&strips=4&slices=1&juices=1&coffee=2 HTTP/1.1
	> User-Agent: curl/7.29.0
	> Host: localhost:8888
	> Accept: */*
	> Content-Type: application/json
	> 

Response:

	< HTTP/1.1 200 OK
	< Server: spray-can/1.2-RC2
	< Date: Mon, 10 Feb 2014 19:36:43 GMT
	< Content-Type: application/json; charset=UTF-8
	< Content-Length: 247
	< 
	{
	  "main": {
	    "eggs": {
	      "num": 2
	    },
	    "bacon": {
	      "strips": 4
	    }
	  },
	  "side": {
	    "toast": {
	      "slices": 1
	    }
	  },
	  "drink": {
	    "juice": {
	      "glasses": 1
	    },
	    "coffee": {
	      "mugs": 2
	    }
	  }
	}

First implementation
============

The first implementation consists of four files. Two files define the objects of the breakfast (Elements.scala and JsonImplicit.scala), one defines the API and the last define the main http listener service.

The breakfast elements
```scala
package com.natalinobusa.breakfast.elements

case class ToastedBread(slices: Int)
case class FriedEggs(num: Int)
case class CrispyBacon(strips: Int)
case class OrangeJuice(glasses: Int)
case class HotCoffee(mugs: Int)

case class MainDish(eggs: FriedEggs, bacon: CrispyBacon)
case class SideDish(toast: ToastedBread)
case class Drinks(juice: OrangeJuice, coffee: HotCoffee)

case class Breakfast(main: MainDish, side: SideDish, drink: Drinks)
```

The json (un-)marshalling

```scala
import spray.json.DefaultJsonProtocol

object JsonImplicits extends DefaultJsonProtocol {
  //main dish
  implicit val impCrispyBacon = jsonFormat1(CrispyBacon)
  implicit val impFriedEggs = jsonFormat1(FriedEggs)
  implicit val impMainDish = jsonFormat2(MainDish)

  //side dish
  implicit val impToastedBread = jsonFormat1(ToastedBread)
  implicit val impSideDish = jsonFormat1(SideDish)

  //drinks
  implicit val impHotCoffee = jsonFormat1(HotCoffee)
  implicit val impOrangeJuice = jsonFormat1(OrangeJuice)
  implicit val impDrinks = jsonFormat2(Drinks)

  //breakfast
  implicit val impBreakfast = jsonFormat3(Breakfast)
}
```


Breakfast service:

```scala
package com.natalinobusa.breakfast

import spray.routing.HttpServiceActor
import spray.routing.directives.PathDirectives._

// our breakfast elements

import elements._

// marshalling breakfast to json

import spray.httpx.SprayJsonSupport.sprayJsonMarshaller
import elements.JsonImplicits._

// Routing embedded in the actor
class BreakfastApiService extends HttpServiceActor {

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

  // this actor only runs our route,
  // but you could add other things here
  def receive = runRoute(serviceRoute)

}
```

Main:

The main is quit straightforward, the akka actor system is defined.   
The service is instantiated and thereafter the service is attached to the IO http listener.


```scala
object Boot extends App {
  implicit val system = ActorSystem()
  
  // create and start our service actor
  val service = system.actorOf(Props[BreakfastApiService], "breakfast-api-service")
  
  // start a new HTTP server with our service actor as the handler
  IO(Http) ! Http.Bind(service, "localhost", port = 8888)

}
```



	