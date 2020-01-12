package com.gmail.maystruks08.hikingfood.ui.adapter.viewholders

import android.view.View
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ShoppingListItemView
import kotlinx.android.synthetic.main.item_shopping_list.view.*

class ShoppingItemHolder(itemView: View, onCLickListenerListener: AdapterCallbacks.OnClickListener<ShoppingListItemView>?, onItemChangeListenerListener: AdapterCallbacks.OnItemChangeListener<ShoppingListItemView>?) : BaseViewHolder<ShoppingListItemView>(itemView, onCLickListenerListener, onItemChangeListenerListener) {

    override fun bind(item: ShoppingListItemView) {
        itemView.tvProductName.text = item.name
        itemView.tvTotalWeight.text = item.totalWeight.toString()
        itemView.tvUnit.text = item.unit
        itemView.cdIsPurchased.isChecked = item.isSelected

        itemView.setOnClickListener {
            onCLickListenerListener?.onClicked(item)
        }
    }
}