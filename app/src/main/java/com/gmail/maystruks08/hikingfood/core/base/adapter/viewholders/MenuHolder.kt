package com.gmail.maystruks08.hikingfood.core.base.adapter.viewholders

import android.view.View
import com.gmail.maystruks08.hikingfood.core.base.adapter.BaseViewHolder
import com.gmail.maystruks08.hikingfood.ui.viewmodels.MenuView
import kotlinx.android.synthetic.main.item_menu.view.*
import java.text.SimpleDateFormat
import java.util.*

class MenuHolder(itemView: View) : BaseViewHolder<MenuView>(itemView) {

    override fun bind(item: MenuView, clickListener: (MenuView) -> Unit) {
        itemView.tvMenuName.text = item.name
        itemView.tvCountOfPeopleValue.text = item.peopleCount.toString()
        itemView.tvMenuDateStartValue.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(item.dateOfStartMenu)
        itemView.tvRelaxDayCountValue.text = item.relaxDayCount.toString()
        itemView.tvStartMenuValue.text = item.startFrom.title.toLowerCase()
        val totalWeightMenuValueStr = "${item.totalWeight / 1000} кг"
        itemView.tvTotalWeightMenuValue.text = totalWeightMenuValueStr
        itemView.tvCountOfReceptionsValue.text = item.countOfReceptions.toString()
        itemView.setOnClickListener { clickListener(item) }
        itemView.setOnClickListener {
            clickListener.invoke(item)
        }
    }
}