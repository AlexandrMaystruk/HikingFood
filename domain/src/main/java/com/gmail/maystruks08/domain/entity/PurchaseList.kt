package com.gmail.maystruks08.domain.entity

class PurchaseList private constructor(val purchaseMap: MutableMap<Product, Int> = mutableMapOf()){

    companion object {
        fun generatePurchaseList(days:List<Day>): PurchaseList{
            val purchaseList = PurchaseList()
            days.forEach { day ->
                day.products.values.forEach { list ->
                    list.forEach {
                        purchaseList.updateProductTotalWeight(it)
                    }
                }
            }
            return purchaseList
        }
    }

    fun updateProductTotalWeight(product: Product){
        purchaseMap[product]?.plus(product.portion.portionForAllPeople)
    }
}