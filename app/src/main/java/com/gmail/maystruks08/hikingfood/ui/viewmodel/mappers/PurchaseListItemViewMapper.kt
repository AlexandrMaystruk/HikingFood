package com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers

import com.gmail.maystruks08.domain.entity.PurchaseListItem
import com.gmail.maystruks08.hikingfood.ui.viewmodel.PurchaseListItemView
import javax.inject.Inject

class PurchaseListItemViewMapper @Inject constructor() {

    private fun fromPurchaseListItem(purchaseListItem: PurchaseListItem) =
        purchaseListItem.let {
            PurchaseListItemView(
                it.product.id,
                it.product.name,
                it.totalWeight,
                it.price,
                it.product.portion.unit
            )
        }

    fun fromPurchaseListItems(purchaseListItems: List<PurchaseListItem>): MutableList<PurchaseListItemView> {
        return purchaseListItems.map { fromPurchaseListItem(it) }.toMutableList()
    }
}