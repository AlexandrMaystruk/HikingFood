package com.gmail.maystruks08.domain.entity

class ProductSet private constructor(id: Long, name: String, portion: Portion, val products: MutableList<Product>) : Product(id, name, portion, null) {


    fun removeProduct(productId: Long){
        products.removeAll { it.id == productId }
    }

    override fun calculatePortionForAllPeople(peopleCount: Int) {
        products.forEach { it.calculatePortionForAllPeople(peopleCount) }
        super.calculatePortionForAllPeople(peopleCount)
    }

    fun updatePortionValue(newValue: Int, productId: Long, peopleCount: Int) {
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

        fun create(id: Long, name: String, products: List<Product>): ProductSet {
            var portionValue = 0
            products.forEach { portionValue += it.portion.value }
            return ProductSet(id, name, Portion(portionValue, portionValue, portionValue), products.toMutableList())
        }
    }
}