package com.gmail.maystruks08.hikingfood.ui.adapter.viewholders

import android.view.View
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductViewSelectable
import kotlinx.android.synthetic.main.item_product_selectable.view.*

class SelectableProductHolder(
    itemView: View,
    onCLickListenerListener: AdapterCallbacks.OnClickListener<ProductViewSelectable>?,
    onItemChangeListenerListener: AdapterCallbacks.OnItemChangeListener<ProductViewSelectable>?
) : BaseViewHolder<ProductViewSelectable>(itemView, onCLickListenerListener, onItemChangeListenerListener) {

    override fun bind(item: ProductViewSelectable) {
        itemView.tvProductName.text = item.productView.name
        itemView.tvProductWeightForOnePeople.text = item.productView.portionForOnePeople.toString()
        itemView.tvProductWeightForAllPeople.text = item.productView.portionForAllPeople.toString()
        itemView.cbIsSelected.isChecked = item.productView.isSelected

        itemView.setOnClickListener {
            onCLickListenerListener?.onClicked(item)
        }
    }
}