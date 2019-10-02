package com.gmail.maystruks08.domain.entity

class Day(
    val number: Int,
    val products: MutableMap<TypeOfMeal, MutableList<Product>> = mutableMapOf(),
    val weightTotalsForOne: MutableMap<TypeOfMeal, Int> = mutableMapOf(),
    val weightTotalsForAll: MutableMap<TypeOfMeal, Int> = mutableMapOf()
) {

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

    fun removeProduct(typeOfMeal: TypeOfMeal, product: Product) {
        products[typeOfMeal]?.remove(product)
        updateTotalWeight(typeOfMeal)
    }

    fun removeProducts(typeOfMeal: TypeOfMeal, productsList: List<Product>) {
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