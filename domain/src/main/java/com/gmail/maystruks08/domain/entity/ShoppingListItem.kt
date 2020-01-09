package com.gmail.maystruks08.domain.entity

import java.util.*

class ShoppingListItem private constructor(val id: Long, val product: Product, var totalWeight: Int){

    constructor(product: Product) : this(UUID.randomUUID().mostSignificantBits, product, product.portion.portionForAllPeople)

    fun increaseWeight(productWeightForAll: Int){
        totalWeight+= productWeightForAll
    }

    fun decreaseWeight(productWeightForAll: Int){
        totalWeight -= productWeightForAll
    }
}