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


