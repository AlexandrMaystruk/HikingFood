package com.gmail.maystruks08.domain.entity

data class FoodMeal(val defProducts: MutableList<Product>, val loopProducts: MutableList<Product>){

    fun calculatePortionForAllPeople(peopleCount: Int){
        defProducts.forEach { it.calculatePortionForAllPeople(peopleCount) }
        loopProducts.forEach { it.calculatePortionForAllPeople(peopleCount) }
    }


    fun updateProductPortionValue(id: Int, newValue: Int, peopleCount: Int){
        defProducts.find { it.id == id }?.let { product->
            val position = defProducts.indexOf(product)
            defProducts.remove(product)
            val updatedProduct =
                if (product is SoupSet) {
                    product.apply { this.updatePortionValue(newValue, id, peopleCount) }
                } else {
                    product.apply {
                        this.portion.value = newValue
                        this.calculatePortionForAllPeople(peopleCount)
                    }
                }
            defProducts.add(position, updatedProduct)
        }

        loopProducts.find { it.id == id }?.let { product->
            val position = loopProducts.indexOf(product)
            loopProducts.remove(product)
            val updatedProduct =
                if (product is SoupSet) {
                    product.apply { this.updatePortionValue(newValue, id, peopleCount) }
                } else {
                    product.apply {
                        this.portion.value = newValue
                        this.calculatePortionForAllPeople(peopleCount)
                    }
                }
            loopProducts.add(position, updatedProduct)
        }
    }
}