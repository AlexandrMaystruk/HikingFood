package com.gmail.maystruks08.domain.entity

import java.util.*

data class StartInquirerInfo(
    var name: String,
    var peopleCount: Int,
    var numberOfReceptions: Int,
    var relaxDayCount: Int,
    var dateOfStartMenu: Date,
    var timeOfStartMenu: TypeOfMeal,
    val products: MutableList<Product>,
    val soupSets: List<SoupSet>,
    val foodMeals: MutableMap<TypeOfMeal, FoodMeal>
) {

    fun getProduct(id: Int): Product? {
        return products.find { it.id == id }
    }

    fun changePortionValue(newValue: Int, productId: Int) {
        //update portion value and change portion value for all people
        updateValueInProducts(newValue, productId)
        updateValueInSoupSets(newValue, productId)
        updateValueInFoodMealsEntity(newValue, productId)
    }

    fun updatePortionValue() {
        products.forEach { it.calculatePortionForAllPeople(peopleCount) }
        foodMeals.values.forEach { it.calculatePortionForAllPeople(peopleCount) }
        soupSets.forEach { it.calculatePortionForAllPeople(peopleCount) }
    }

    private fun updateValueInProducts(newValue: Int, productId: Int) {
        products.find { it.id == productId }?.let { product ->
            val position = products.indexOf(product)
            products.remove(product)
            val updatedProduct =
                if (product is SoupSet) {
                    product.apply {
                        this.updatePortionValue(newValue, productId, peopleCount)
                    }
                } else {
                    product.apply {
                        this.portion.value = newValue
                        this.calculatePortionForAllPeople(peopleCount)
                    }
                }
            products.add(position, updatedProduct)
        }
    }

    private fun updateValueInFoodMealsEntity(newValue: Int, productId: Int) {
        //update in all containers
        foodMeals[TypeOfMeal.BREAKFAST]?.apply {
            this.updateProductPortionValue(newValue, productId, peopleCount)
        }?.let { foodMeals[TypeOfMeal.BREAKFAST] = it }

        foodMeals[TypeOfMeal.LUNCH]?.apply {
            this.updateProductPortionValue(newValue, productId, peopleCount)
        }?.let { foodMeals[TypeOfMeal.LUNCH] = it }

        foodMeals[TypeOfMeal.DINNER]?.apply {
            this.updateProductPortionValue(newValue, productId, peopleCount)
        }?.let { foodMeals[TypeOfMeal.DINNER] = it }
    }
    private fun updateValueInSoupSets(newValue: Int, productId: Int) {
        soupSets.forEach { it.updatePortionValue(newValue, productId, peopleCount) }
    }
}
