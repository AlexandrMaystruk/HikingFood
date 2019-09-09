package com.gmail.maystruks08.domain.entity

class SoupSet private constructor(id: Int, name: String, portion: Portion, val products: List<Product>) :
    Product(id, name, portion) {

    override fun calculatePortionForAllPeople(peopleCount: Int) {
        products.forEach { it.calculatePortionForAllPeople(peopleCount) }
        super.calculatePortionForAllPeople(peopleCount)
    }

    fun updatePortionValue(newValue: Int, productId: Int, peopleCount: Int) {
        var parentPortionValue = 0
        var parentPortionValueMin = 0
        var parentPortionValueMax = 0
        var parentPortionForAllPeopleValue = 0
        products.forEach {
            if (productId == it.id) {
                it.portion.value = newValue
                it.calculatePortionForAllPeople(peopleCount)
            }
            parentPortionValue += it.portion.value
            parentPortionValueMin += it.portion.min
            parentPortionValueMax += it.portion.max
            parentPortionForAllPeopleValue += it.portion.portionForAllPeople
        }
        this.portion = Portion(parentPortionValue, parentPortionValueMin, parentPortionValueMax)
        super.calculatePortionForAllPeople(peopleCount)
    }

    companion object {

        fun create(id: Int, name: String, products: List<Product>): SoupSet {
            var portionValue = 0
            products.forEach { portionValue += it.portion.value }
            return SoupSet(id, name, Portion(portionValue, portionValue, portionValue), products)
        }
    }
}