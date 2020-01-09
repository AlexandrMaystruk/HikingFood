package com.gmail.maystruks08.domain.entity

import java.util.*

class Menu private constructor(
    val id: Long,
    var name: String,
    var peopleCount: Int,
    var numberOfReceptions: Int,
    var restDayCount: Int,
    var dateOfStartMenu: Date,
    var startFrom: TypeOfMeal,
    val defaultProductList: MutableList<Product> = mutableListOf(),
    val days: MutableList<Day> = mutableListOf(),
    var totalWeight: Int = 0
) {

    fun getProductById(id: Long): Product? = defaultProductList.find { it.id == id }

    fun deleteDay(day: Day) {
        days.remove(day)
        totalWeight -= day.getDayTotalWeightForAll()
    }

    fun deleteProductFromAllDays(product: Product) {
        defaultProductList.remove(product)
        days.forEach { totalWeight -= it.removeProductFromDay(product) }
    }

    fun deleteProductFromDayByTypeOfMeal(dayNumber: Int, typeOfMeal: TypeOfMeal, product: Product) {
        days.find { it.number == dayNumber }?.let {
            val index = days.indexOf(it)
            totalWeight -= days[index].removeProductFromMeal(typeOfMeal, product)
        }
    }

    fun deleteProductFromDay(dayNumber: Int, product: Product) {
        days.find { it.number == dayNumber }?.let {
            val index = days.indexOf(it)
            totalWeight -= days[index].removeProductFromDay(product)
        }
    }

    companion object {

        fun generateMenu(startInfo: StartInquirerInfo): Menu {
            return startInfo.let { inquirerInfo ->
                val countReceptionInDay = 3
                val dayList = mutableListOf<Day>()
                var dayNumber = 1
                var totalWeight = 0
                val relaxDayNumber = if (inquirerInfo.relaxDayCount > 0) inquirerInfo.numberOfReceptions / countReceptionInDay / 2 + 1 else -1
                var day = Day(dayNumber, inquirerInfo.dateOfStartMenu)
                //stores indexes for loop products list,
                //loopIndexHolder[0] -> indexBreakfast
                //loopIndexHolder[1] -> indexLunch
                //loopIndexHolder[2] -> indexDinner
                val loopIndexHolder = arrayOf(0, 0, 0)
                var currentTypeOfMeal = inquirerInfo.timeOfStartMenu
                for (number in 0 until inquirerInfo.numberOfReceptions){
                    inquirerInfo.foodMeals[currentTypeOfMeal]?.let { foodMeal ->
                        //add def products
                        day.addProducts(currentTypeOfMeal, foodMeal.defProducts)
                        val currentIndex = loopIndexHolder[currentTypeOfMeal.type]
                        //add loop products
                        day.addProduct(currentTypeOfMeal, foodMeal.loopProducts[currentIndex])
                        if (foodMeal.loopProducts.lastIndex == currentIndex) {
                            loopIndexHolder[currentTypeOfMeal.type] = 0
                        } else {
                            loopIndexHolder[currentTypeOfMeal.type]++
                        }
                    }
                    //get next food meal
                    currentTypeOfMeal = TypeOfMeal.getNextMeal(currentTypeOfMeal)

                    if (day.isDayComplete(startInfo.timeOfStartMenu) || number == inquirerInfo.numberOfReceptions - 1) {
                        totalWeight += day.getDayTotalWeightForAll()
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
                    defaultProductList = inquirerInfo.products.plus(inquirerInfo.productSets).toMutableList(),
                    days = dayList,
                    totalWeight = totalWeight
                )
            }
        }
    }
}