package com.gmail.maystruks08.hikingfood.core.base.adapter.viewholders

import android.view.View
import androidx.core.content.ContextCompat
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.core.base.adapter.BaseViewHolder
import com.gmail.maystruks08.hikingfood.ui.viewmodels.DayView
import kotlinx.android.synthetic.main.item_day.view.*

class DayHolder(itemView: View) : BaseViewHolder<DayView>(itemView) {

    override fun bind(item: DayView, clickListener: (DayView) -> Unit) {
        if (item.isRelaxDay) {
            itemView.cvDay.setCardBackgroundColor(ContextCompat.getColor(itemView.context, (R.color.colorGrey)))
            itemView.tvProductName.text = itemView.context.getString(R.string.relax_day)
        } else {
            itemView.cvDay.setCardBackgroundColor(null)
            itemView.tvProductName.text = itemView.context.getString(R.string.day)
        }
        itemView.tvDayNumberValue.text = item.number.toString()
        itemView.tvTotalWeightValue.text = (item.breakfastTotalWeight + item.lunchTotalWeight + item.dinnerTotalWeight).toString()
        itemView.tvTotalWeightForAllValue.text = (item.breakfastTotalWeightForAll + item.lunchTotalWeightForAll + item.dinnerTotalWeightForAll).toString()
        itemView.setOnClickListener { clickListener(item) }
    }
}