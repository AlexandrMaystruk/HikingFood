package com.gmail.maystruks08.domain.entity

data class ShoppingListItem(val product: Product, var totalWeight: Int, var price: Double = 0.0){

    fun increaseWeight(productWeightForAll: Int){
        totalWeight+= productWeightForAll
    }

    fun decreaseWeight(productWeightForAll: Int){
        totalWeight -= productWeightForAll
    }
}