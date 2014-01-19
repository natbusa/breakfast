package com.natalinobusa.breakfast

package elements {

  case class Bacon(strips:Int)
  case class Eggs(n:Int)
  case class Bread(slices:Int)
  case class Oranges(n:Int)
  case class CoffeBeans(grams:Int)
  
  case class ToastedBread(slices:Int)
  case class FriedEggs(num:Int)
  case class CrispyBacon(strips:Int)
  case class OrangeJuice(glasses:Int)
  case class HotCoffee(mugs:Int)

  case class MainDish(friedEggs: FriedEggs, cripsyBacon:CrispyBacon)
  
  case class Breakfast(mainDish: MainDish, toastedBread:ToastedBread, orangeJuice:OrangeJuice, hotCoffee:HotCoffee)
}