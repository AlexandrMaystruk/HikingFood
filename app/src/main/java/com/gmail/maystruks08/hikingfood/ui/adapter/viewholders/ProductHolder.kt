package com.gmail.maystruks08.hikingfood.ui.adapter.viewholders

import android.view.View
import android.widget.LinearLayout
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductView
import com.gmail.maystruks08.hikingfood.utils.extensions.toPx
import kotlinx.android.synthetic.main.item_product.view.*

class ProductHolder(
    itemView: View,
    onCLickListenerListener: AdapterCallbacks.OnClickListener<ProductView>?,
    onItemChangeListenerListener: AdapterCallbacks.OnItemChangeListener<ProductView>?
) : BaseViewHolder<ProductView>(itemView, onCLickListenerListener, onItemChangeListenerListener) {

    override fun bind(item: ProductView) {
        itemView.tvProductName.text = item.name
        itemView.tvProductWeightForOnePeople.text = item.portionForOnePeople.toString()
        itemView.tvProductWeightForAllPeople.text = item.portionForAllPeople.toString()
        if (item.parentId != null) setLeftMargin(itemView.resources.getDimension(R.dimen.margin_xs)) else setLeftMargin(0f)
        itemView.setOnClickListener {
            onCLickListenerListener?.onClicked(item)
        }
    }

    private fun setLeftMargin(value: Float) {
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.leftMargin = itemView.resources.displayMetrics.toPx(value).toInt()
        itemView.layoutParams = layoutParams
    }
}