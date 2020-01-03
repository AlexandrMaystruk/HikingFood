package com.gmail.maystruks08.hikingfood.core.base.adapter.viewholders

import android.view.View
import com.gmail.maystruks08.hikingfood.core.base.adapter.BaseViewHolder
import com.gmail.maystruks08.hikingfood.ui.viewmodels.ProductPortionView
import kotlinx.android.synthetic.main.item_product_portion.view.*

class ProductPortionHolder(itemView: View) : BaseViewHolder<ProductPortionView>(itemView) {

    override fun bind(item: ProductPortionView, clickListener: (ProductPortionView) -> Unit) {
        itemView.tvProductName.text = item.name
        itemView.npRelaxDayCountValue.minValue = item.portionMin
        itemView.npRelaxDayCountValue.maxValue = item.portionMax
        itemView.npRelaxDayCountValue.value = item.portionValue
        itemView.npRelaxDayCountValue.wrapSelectorWheel = false
        itemView.setOnClickListener {
            clickListener.invoke(item)
        }
    }
}