package com.natalinobusa.breakfast.elements

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
