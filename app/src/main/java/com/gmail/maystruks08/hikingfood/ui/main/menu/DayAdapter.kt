package com.gmail.maystruks08.hikingfood.ui.main.menu

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView
import kotlinx.android.synthetic.main.item_day.view.*

import kotlin.properties.Delegates

class DayAdapter(private val clickListener: (DayView) -> Unit) :
    RecyclerView.Adapter<DayAdapter.ViewHolder>() {

    var dayList: MutableList<DayView> by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        dayList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHolder(dayList[position], clickListener)
    }

    override fun getItemCount(): Int = dayList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindHolder(dayView: DayView, clickListener: (DayView) -> Unit) {
            itemView.tvDayNumberValue.text = dayView.number.toString()
            itemView.tvTotalWeightValue.text = (dayView.breakfastTotalWeight + dayView.lunchTotalWeight + dayView.dinnerTotalWeight).toString()
            itemView.tvTotalWeightForAllValue.text = (dayView.breakfastTotalWeightForAll + dayView.lunchTotalWeightForAll + dayView.dinnerTotalWeightForAll).toString()
            itemView.setOnClickListener { clickListener(dayView) }
        }
    }
}