package com.gmail.maystruks08.hikingfood.core.base.adapter.viewholders

import android.view.View
import com.gmail.maystruks08.hikingfood.core.base.adapter.BaseViewHolder
import com.gmail.maystruks08.hikingfood.ui.viewmodels.ProductView

class ProductHolder(itemView: View) : BaseViewHolder<ProductView>(itemView) {

    override fun bind(item: ProductView, clickListener: (ProductView) -> Unit) {
        itemView.setOnClickListener {
            clickListener.invoke(item)
        }
    }
}