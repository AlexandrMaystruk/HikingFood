package com.gmail.maystruks08.domain.entity

import java.util.*

open class Day(
    val number: Int,
    val date: Date,
    val products: MutableMap<TypeOfMeal, MutableList<Product>> = mutableMapOf(),
    val weightTotalsForOne: MutableMap<TypeOfMeal, Int> = mutableMapOf(),
    val weightTotalsForAll: MutableMap<TypeOfMeal, Int> = mutableMapOf()
) {

    fun getDayMealCount() = products.keys.size

    fun isDayComplete(startFrom: TypeOfMeal = TypeOfMeal.BREAKFAST): Boolean {
        val isBreakfastComplete = products[TypeOfMeal.BREAKFAST]?.isNotEmpty() ?: false
        val isLunchComplete = products[TypeOfMeal.LUNCH]?.isNotEmpty() ?: false
        val isDinnerComplete = products[TypeOfMeal.DINNER]?.isNotEmpty() ?: false
        return if (number == 1) {
            when (startFrom) {
                TypeOfMeal.BREAKFAST -> isBreakfastComplete && isLunchComplete && isDinnerComplete
                TypeOfMeal.LUNCH -> isLunchComplete && isDinnerComplete
                TypeOfMeal.DINNER -> isDinnerComplete
            }
        } else {
            isBreakfastComplete && isLunchComplete && isDinnerComplete
        }
    }

    fun getDayTotalWeightForOne(): Int {
        return (weightTotalsForOne[TypeOfMeal.BREAKFAST] ?: 0) +
                (weightTotalsForOne[TypeOfMeal.LUNCH] ?: 0) +
                (weightTotalsForOne[TypeOfMeal.DINNER] ?: 0)
    }

    fun getDayTotalWeightForAll(): Int {
        return (weightTotalsForAll[TypeOfMeal.BREAKFAST] ?: 0) +
                (weightTotalsForAll[TypeOfMeal.LUNCH] ?: 0) +
                (weightTotalsForAll[TypeOfMeal.DINNER] ?: 0)
    }

    fun addProduct(typeOfMeal: TypeOfMeal, product: Product) {
        if (products[typeOfMeal] == null) {
            products[typeOfMeal] = mutableListOf()
        }
        products[typeOfMeal]?.add(product)
        updateTotalWeight(typeOfMeal)
    }

    fun addProducts(typeOfMeal: TypeOfMeal, productsList: List<Product>) {
        if (products[typeOfMeal] == null) {
            products[typeOfMeal] = mutableListOf()
        }
        products[typeOfMeal]?.addAll(productsList)
        updateTotalWeight(typeOfMeal)
    }

    fun removeProductFromMeal(typeOfMeal: TypeOfMeal, product: Product) {
        products[typeOfMeal]?.remove(product)
        updateTotalWeight(typeOfMeal)
    }

    fun removeProductFromDay(product: Product) {
        removeProductFromMeal(TypeOfMeal.BREAKFAST, product)
        removeProductFromMeal(TypeOfMeal.LUNCH, product)
        removeProductFromMeal(TypeOfMeal.DINNER, product)
    }

    fun removeProductsFromMeal(typeOfMeal: TypeOfMeal, productsList: List<Product>) {
        products[typeOfMeal]?.removeAll(productsList)
        updateTotalWeight(typeOfMeal)
    }

    private fun updateTotalWeight(typeOfMeal: TypeOfMeal) {
        var currentWeightForAll = 0
        var currentWeightForOne = 0
        products[typeOfMeal]?.forEach {
            currentWeightForAll += it.portion.portionForAllPeople
            currentWeightForOne += it.portion.value
        }
        weightTotalsForAll[typeOfMeal] = currentWeightForAll
        weightTotalsForOne[typeOfMeal] = currentWeightForOne
    }
}