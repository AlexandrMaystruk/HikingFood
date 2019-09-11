package com.gmail.maystruks08.domain.entity

class Day(
    val number: Int,
    val products: MutableMap<TypeOfMeal, MutableList<Product>> = mutableMapOf(),
    val weightTotals: MutableMap<TypeOfMeal, Int> = mutableMapOf()
) {

    fun getDayTotalWeight(): Int {
        return (weightTotals[TypeOfMeal.BREAKFAST] ?: 0) +
                (weightTotals[TypeOfMeal.LUNCH] ?: 0) +
                (weightTotals[TypeOfMeal.DINNER] ?: 0)
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
        var currentWeight =  0
        products[typeOfMeal]?.forEach {
            currentWeight += it.portion.portionForAllPeople
        }
        weightTotals[typeOfMeal] = currentWeight
    }
}