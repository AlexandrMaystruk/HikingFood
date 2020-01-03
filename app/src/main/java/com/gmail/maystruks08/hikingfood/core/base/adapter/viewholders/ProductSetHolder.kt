package com.gmail.maystruks08.hikingfood.core.base.adapter.viewholders

import android.view.View
import com.gmail.maystruks08.hikingfood.core.base.adapter.BaseViewHolder
import com.gmail.maystruks08.hikingfood.ui.viewmodels.SetProductView

class ProductSetHolder(itemView: View) : BaseViewHolder<SetProductView>(itemView) {

    override fun bind(item: SetProductView, clickListener: (SetProductView) -> Unit) {
        itemView.setOnClickListener {
            clickListener.invoke(item)
        }
    }
}