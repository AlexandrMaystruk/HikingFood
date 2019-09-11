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
                    when ((number + 3) % 3) {
                        0 -> {
                            inquirerInfo.foodMeals[TypeOfMeal.BREAKFAST]?.let { foodMeal ->
                                day.addProducts(TypeOfMeal.BREAKFAST, foodMeal.defProducts)
                                if (foodMeal.loopProducts.lastIndex == indexBreakfast) {
                                    day.addProduct(
                                        TypeOfMeal.BREAKFAST,
                                        foodMeal.loopProducts[indexBreakfast]
                                    )
                                    indexBreakfast = 0
                                } else {
                                    day.addProduct(
                                        TypeOfMeal.BREAKFAST,
                                        foodMeal.loopProducts[indexBreakfast]
                                    )
                                    indexBreakfast++
                                }
                            }
                        }
                        1 -> {
                            inquirerInfo.foodMeals[TypeOfMeal.LUNCH]?.let { foodMeal ->
                                day.addProducts(TypeOfMeal.LUNCH, foodMeal.defProducts)
                                if (foodMeal.loopProducts.lastIndex == indexLunch) {
                                    day.addProduct(
                                        TypeOfMeal.LUNCH,
                                        foodMeal.loopProducts[indexBreakfast]
                                    )
                                    indexLunch = 0
                                } else {
                                    day.addProduct(
                                        TypeOfMeal.LUNCH,
                                        foodMeal.loopProducts[indexBreakfast]
                                    )
                                    indexLunch++
                                }
                            }

                        }
                        2 -> {
                            inquirerInfo.foodMeals[TypeOfMeal.DINNER]?.let { foodMeal ->
                                day.addProducts(TypeOfMeal.DINNER, foodMeal.defProducts)
                                if (foodMeal.loopProducts.lastIndex == indexDinner) {
                                    day.addProduct(
                                        TypeOfMeal.DINNER,
                                        foodMeal.loopProducts[indexBreakfast]
                                    )
                                    indexDinner = 0
                                } else {
                                    day.addProduct(
                                        TypeOfMeal.DINNER,
                                        foodMeal.loopProducts[indexBreakfast]
                                    )
                                    indexDinner++
                                }
                            }
                        }
                    }

                    if ((indexLunch == indexBreakfast && indexBreakfast == indexDinner) || number == inquirerInfo.numberOfReceptions - 1) {
                        dayList.add(day)
                        dayNumber++
                        day = Day(dayNumber)
                    }
//
//                    if (number % 3 == 0 && number != 0 || number == inquirerInfo.numberOfReceptions) {
//
//                    }
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