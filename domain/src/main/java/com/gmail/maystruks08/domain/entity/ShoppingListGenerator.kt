package com.gmail.maystruks08.domain.entity

import javax.inject.Inject

class ShoppingListGenerator @Inject constructor() {

    /** GET purchase list group by TOTAL SUM */
    fun groupShoppingListByProduct(days: List<Day>): List<ShoppingListItem> {
        return mutableListOf<ShoppingListItem>().apply {
            days.forEach { day ->
                day.products.values.forEach { productList ->
                    productList.forEach { product ->
                        if (product is ProductSet) {
                            product.products.forEach { updateProductTotalWeight( it, this) }
                        } else {
                            updateProductTotalWeight(product, this)
                        }
                    }
                }
            }
        }
    }

    /** GET purchase map where key is StoreDepartment, value is PurchaseListItem. Group by STORE DEPARTMENT */
    fun groupShoppingListByStoreDepartment(days: List<Day>): Map<StoreDepartment, List<ShoppingListItem>> {
        return mutableMapOf<StoreDepartment, MutableList<ShoppingListItem>>().apply {
            days.forEach { day ->
                day.products.values.forEach { productList ->
                    productList.forEach { product ->
                        createNew(product = product, map = this)
                    }
                }
            }
        }
    }

    /** GET purchase map where key is StoreDepartment, value is PurchaseListItem. Group by PRODUCT AND STORE DEPARTMENT */
    fun groupShoppingListByProductAndStoreDepartment(days: List<Day>): Map<StoreDepartment, List<ShoppingListItem>> {
        return mutableMapOf<StoreDepartment, MutableList<ShoppingListItem>>().apply {
            days.forEach { day ->
                day.products.values.forEach { productList ->
                    productList.forEach { product ->
                        createNewOrUpdateExisted(product, this)
                    }
                }
            }
        }
    }

    private fun createNew(product: Product, map: MutableMap<StoreDepartment, MutableList<ShoppingListItem>>) {
        val newShoppingListItemUnic = ShoppingListItem(product)
        val storeDepartmentProducts = map[product.storeDepartment]
        if (storeDepartmentProducts.isNullOrEmpty()) {
            product.storeDepartment?.let {
                map[it] = mutableListOf(newShoppingListItemUnic)
            }
        } else storeDepartmentProducts.add(newShoppingListItemUnic)
    }

    private fun createNewOrUpdateExisted(product: Product, map: MutableMap<StoreDepartment, MutableList<ShoppingListItem>>) {
        if (product is ProductSet) {
            product.products.forEach { childProduct ->
                val products = map[childProduct.storeDepartment]
                if (products.isNullOrEmpty()) {
                    childProduct.storeDepartment?.let {
                        map[it] = mutableListOf(ShoppingListItem(product))
                    }
                } else updateProductTotalWeight(childProduct, products)
            }
        } else {
            val products = map[product.storeDepartment]
            if (products.isNullOrEmpty()) {
                product.storeDepartment?.let {
                    map[it] = mutableListOf(ShoppingListItem(product))
                }
            } else updateProductTotalWeight(product, products)
        }
    }

    private fun updateProductTotalWeight(product: Product, items: MutableList<ShoppingListItem>) {
        val purchaseListItem = items.find { it.product.id == product.id }
        if (purchaseListItem != null) {
            val index = items.indexOf(purchaseListItem)
            items[index].increaseWeight(product.portion.portionForAllPeople)
        } else {
            items.add(ShoppingListItem(product))
        }
    }
}