package com.gmail.maystruks08.hikingfood.ui.adapter.viewholders

import android.view.View
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductView
import kotlinx.android.synthetic.main.item_product.view.*

class ProductHolder(itemView: View, onCLickListenerListener: AdapterCallbacks.OnClickListener<ProductView>?, onItemChangeListenerListener: AdapterCallbacks.OnItemChangeListener<ProductView>?) : BaseViewHolder<ProductView>(itemView, onCLickListenerListener, onItemChangeListenerListener) {

    override fun bind(item: ProductView) {
        itemView.tvProductName.text = item.name
        itemView.tvProductWeightForOnePeople.text = item.portionForOnePeople.toString()
        itemView.tvProductWeightForAllPeople.text = item.portionForAllPeople.toString()

        itemView.setOnClickListener {
            onCLickListenerListener?.onClicked(item)
        }
    }
}