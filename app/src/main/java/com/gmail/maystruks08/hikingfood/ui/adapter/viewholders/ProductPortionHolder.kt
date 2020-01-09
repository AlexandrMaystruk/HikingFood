package com.gmail.maystruks08.hikingfood.ui.adapter.viewholders

import android.view.View
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductPortionView
import kotlinx.android.synthetic.main.item_product_portion.view.*

class ProductPortionHolder(itemView: View,
    onCLickListenerListener: AdapterCallbacks.OnClickListener<ProductPortionView>?,
    onItemChangeListenerListener: AdapterCallbacks.OnItemChangeListener<ProductPortionView>?
) : BaseViewHolder<ProductPortionView>(itemView, onCLickListenerListener, onItemChangeListenerListener) {

    override fun bind(item: ProductPortionView) {
        itemView.tvProductName.text = item.name
        itemView.npRelaxDayCountValue.minValue = item.portionMin
        itemView.npRelaxDayCountValue.maxValue = item.portionMax
        itemView.npRelaxDayCountValue.value = item.portionValue
        itemView.npRelaxDayCountValue.wrapSelectorWheel = false

        itemView.npRelaxDayCountValue.setOnValueChangedListener { _, _, newVal ->
            val changedValue = ProductPortionView(
                id = item.id,
                name = item.name,
                portionMin = item.portionMin,
                portionMax = item.portionMax,
                portionValue = newVal
            )
            onItemChangeListenerListener?.onItemChanged(changedValue)
        }
    }
}