package com.gmail.maystruks08.domain.entity

class PurchaseList private constructor(val purchaseListItems: MutableList<PurchaseListItem> = mutableListOf()) {

    companion object {
        fun generatePurchaseList(days: List<Day>): PurchaseList {
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

    fun updateProductTotalWeight(product: Product) {
        val purchaseListItem = purchaseListItems.find { it.product == product }
        if (purchaseListItem != null) {
            val index = purchaseListItems.indexOf(purchaseListItem)
            purchaseListItems[index].increaseWeight(product.portion.portionForAllPeople)
        } else {
            purchaseListItems.add(PurchaseListItem(product, product.portion.portionForAllPeople))
        }
    }

    fun removeItem(product: Product): Int {
        var totalRemovedWeight = 0
        purchaseListItems.removeAll {
            if (it.product == product) {
                totalRemovedWeight += product.portion.portionForAllPeople
            }
            it.product == product
        }
        return totalRemovedWeight
    }

    fun decreaseProduct(product: Product): Int  {
        purchaseListItems.find { it.product == product }?.apply {
            decreaseWeight(this.product.portion.portionForAllPeople)
        }?.let {
            purchaseListItems.add(it)
            return product.portion.portionForAllPeople
        }
        return 0
    }
}