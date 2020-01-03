package com.gmail.maystruks08.hikingfood.core.base.adapter.viewholders

import android.view.View
import com.gmail.maystruks08.hikingfood.core.base.adapter.BaseViewHolder
import com.gmail.maystruks08.hikingfood.ui.viewmodels.ShoppingListItemView
import kotlinx.android.synthetic.main.item_shopping_list.view.*

class ShoppingItemHolder(itemView: View) : BaseViewHolder<ShoppingListItemView>(itemView) {


    override fun bind(item: ShoppingListItemView, clickListener: (ShoppingListItemView) -> Unit) {
        itemView.tvProductName.text = item.name
        itemView.tvTotalWeight.text = item.totalWeight.toString()
        itemView.tvUnit.text = item.unit.type
        itemView.tvPosition.isChecked = item.isPurchased
        itemView.setOnClickListener {
            clickListener.invoke(item)
        }

        // val index = shoppingListItems.indexOf(item)
//            if (index != -1) {
//                shoppingListItems[index].isPurchased = !item.isPurchased
//                itemView.tvPosition.isChecked = !itemView.tvPosition.isChecked
//            }
    }
}