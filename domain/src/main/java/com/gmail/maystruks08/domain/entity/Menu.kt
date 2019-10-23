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
    var days: List<Day>,
    val purchaseList: PurchaseList
) {

    companion object {
        fun create(startInfo: StartInquirerInfo): Menu {
            return startInfo.let { inquirerInfo ->
                val countReceptionInDay = 3
                val dayList = mutableListOf<Day>()
                var indexBreakfast = 0
                var indexLunch = 0
                var indexDinner = 0
                var dayNumber = 1
                val relaxDayNumber =
                    if (inquirerInfo.relaxDayCount > 0) inquirerInfo.numberOfReceptions / countReceptionInDay / 2 + 1 else -1
                var day = Day(dayNumber, inquirerInfo.dateOfStartMenu)
                for (number in 0 until inquirerInfo.numberOfReceptions) {
                    //need to shift index if days start from lunch or dinner
                    val index = if (dayNumber != 1) {
                        (number + countReceptionInDay) % countReceptionInDay
                    } else {
                        (number + inquirerInfo.timeOfStartMenu.ordinal + countReceptionInDay) % countReceptionInDay
                    }
                    when (index) {
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
                                        foodMeal.loopProducts[indexLunch]
                                    )
                                    indexLunch = 0
                                } else {
                                    day.addProduct(
                                        TypeOfMeal.LUNCH,
                                        foodMeal.loopProducts[indexLunch]
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
                                        foodMeal.loopProducts[indexDinner]
                                    )
                                    indexDinner = 0
                                } else {
                                    day.addProduct(
                                        TypeOfMeal.DINNER,
                                        foodMeal.loopProducts[indexDinner]
                                    )
                                    indexDinner++
                                }
                            }
                        }
                    }

                    if (day.isDayComplete(startInfo.timeOfStartMenu) || number == inquirerInfo.numberOfReceptions - 1) {
                        dayList.add(day)
                        dayNumber++
                        day = if (dayNumber != relaxDayNumber) {
                            Day(dayNumber, Date())
                        } else {
                            RelaxDay(dayNumber, Date())
                        }
                    }
                }

                Menu(
                    id = Date().time,
                    name = inquirerInfo.name,
                    peopleCount = inquirerInfo.peopleCount,
                    numberOfReceptions = inquirerInfo.numberOfReceptions,
                    restDayCount = inquirerInfo.relaxDayCount,
                    dateOfStartMenu = inquirerInfo.dateOfStartMenu,
                    startFrom = inquirerInfo.timeOfStartMenu,
                    defaultProductList = inquirerInfo.products,
                    days = dayList,
                    purchaseList = PurchaseList.generatePurchaseList(dayList)
                )
            }
        }
    }
}