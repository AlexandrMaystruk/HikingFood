package com.gmail.maystruks08.hikingfood.ui.adapter.viewholders

import android.graphics.Typeface
import android.view.View
import androidx.core.content.ContextCompat
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.SetProductView
import kotlinx.android.synthetic.main.item_product_set.view.*
import kotlinx.android.synthetic.main.item_product_set.view.ivProductSetIcon
import kotlinx.android.synthetic.main.item_product_set.view.tvProductWeightForAllPeople
import kotlinx.android.synthetic.main.item_product_set.view.tvProductWeightForOnePeople

class ProductSetHolder(itemView: View, onCLickListenerListener: AdapterCallbacks.OnClickListener<SetProductView>?, onItemChangeListenerListener: AdapterCallbacks.OnItemChangeListener<SetProductView>?
) : BaseViewHolder<SetProductView>(itemView, onCLickListenerListener, onItemChangeListenerListener) {

    override fun bind(item: SetProductView) {
        itemView.tvProductSetName.text = item.name
        itemView.tvProductWeightForOnePeople.text = item.portionForOnePeople.toString()
        itemView.tvProductWeightForAllPeople.text = item.portionForAllPeople.toString()
        itemView.tvProductSetName.setTypeface(null, Typeface.BOLD)
        itemView.tvProductWeightForOnePeople.setTypeface(null, Typeface.BOLD)
        itemView.tvProductWeightForAllPeople.setTypeface(null, Typeface.BOLD)
        if (item.isSelected) {
            itemView.ivProductSetIcon.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_expand_more))
        } else {
            itemView.ivProductSetIcon.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.ic_arrow_right))
        }
        itemView.setOnClickListener { onCLickListenerListener?.onClicked(item) }
    }
}