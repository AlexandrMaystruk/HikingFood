package com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers

import com.gmail.maystruks08.domain.entity.ShoppingListItem
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ShoppingListItemView
import javax.inject.Inject

class ShoppingListItemViewMapper @Inject constructor() {

    private fun fromPurchaseListItem(shoppingListItem: ShoppingListItem) =
        shoppingListItem.let {
            ShoppingListItemView(
                it.product.id,
                it.product.name,
                it.totalWeight,
                it.price,
                it.product.portion.unit
            )
        }

    fun fromShoppingListItems(shoppingListItems: List<ShoppingListItem>): MutableList<ShoppingListItemView> {
        return shoppingListItems.map { fromPurchaseListItem(it) }.toMutableList()
    }
}