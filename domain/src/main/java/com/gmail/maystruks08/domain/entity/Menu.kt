package com.gmail.maystruks08.domain.entity

import java.util.*

data class Menu(
    val id: Long,
    var name: String,
    var peopleCount: Int,
    var numberOfReceptions: Int,
    var restDayCount: Int,
    var dateOfStartMenu: Date,
    var startFrom: TypeOfMeal,
    var defaultProductList: List<Product>,
    var menu: List<Day>
) {

    companion object {
        fun create(startInfo: StartInquirerInfo): Menu {
            return startInfo.let { inquirerInfo ->

                val dayList = mutableListOf<Day>()
                var indexBreakfast = 0
                var indexLunch = 0
                var indexDinner = 0
                var dayNumber = 0

                var day = Day(dayNumber)
                for (number in 0 until inquirerInfo.numberOfReceptions) {
                    if (number % 3 == 1) {
                        dayList.add(day)
                        dayNumber++
                        day = Day(dayNumber)
                    }
                    when ((number + 3) % 3) {
                        0 -> {
                            inquirerInfo.foodMeals[TypeOfMeal.BREAKFAST]?.let { foodMeal ->
                                day.products[TypeOfMeal.BREAKFAST]?.addAll(foodMeal.defProducts)
                                if (foodMeal.loopProducts.lastIndex == indexBreakfast) {
                                    day.products[TypeOfMeal.BREAKFAST]?.add(foodMeal.loopProducts[indexBreakfast])
                                    indexBreakfast = 0
                                } else {
                                    day.products[TypeOfMeal.BREAKFAST]?.add(foodMeal.loopProducts[indexBreakfast])
                                    indexBreakfast++
                                }

                            }
                        }
                        1 -> {
                            inquirerInfo.foodMeals[TypeOfMeal.LUNCH]?.let { foodMeal ->
                                day.products[TypeOfMeal.LUNCH]?.addAll(foodMeal.defProducts)
                                if (foodMeal.loopProducts.lastIndex == indexLunch) {
                                    day.products[TypeOfMeal.LUNCH]?.add(foodMeal.loopProducts[indexLunch])
                                    indexLunch = 0
                                } else {
                                    day.products[TypeOfMeal.LUNCH]?.add(foodMeal.loopProducts[indexLunch])
                                    indexLunch++
                                }
                            }

                        }
                        2 -> {
                            inquirerInfo.foodMeals[TypeOfMeal.DINNER]?.let { foodMeal ->
                                day.products[TypeOfMeal.DINNER]?.addAll(foodMeal.defProducts)
                                if (foodMeal.loopProducts.lastIndex == indexDinner) {
                                    day.products[TypeOfMeal.DINNER]?.add(foodMeal.loopProducts[indexDinner])
                                    indexDinner = 0
                                } else {
                                    day.products[TypeOfMeal.DINNER]?.add(foodMeal.loopProducts[indexDinner])
                                    indexDinner++
                                }
                            }
                        }
                    }
                }

                inquirerInfo.let {
                    Menu(
                        id = Date().time,
                        name = inquirerInfo.name,
                        peopleCount = it.peopleCount,
                        numberOfReceptions = inquirerInfo.numberOfReceptions,
                        restDayCount = inquirerInfo.relaxDayCount,
                        dateOfStartMenu = inquirerInfo.dateOfStartMenu,
                        startFrom = inquirerInfo.timeOfStartMenu,
                        defaultProductList = inquirerInfo.products,
                        menu = dayList
                    )
                }
            }
        }
    }

}