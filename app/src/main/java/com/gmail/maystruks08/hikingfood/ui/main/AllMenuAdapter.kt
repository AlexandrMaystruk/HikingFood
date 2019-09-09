package com.gmail.maystruks08.hikingfood.ui.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.ui.viewmodel.MenuView
import kotlinx.android.synthetic.main.item_card_menu.view.*
import java.text.SimpleDateFormat
import java.util.*

import kotlin.properties.Delegates

class AllMenuAdapter(private val clickListener: (MenuView) -> Unit) :
    RecyclerView.Adapter<AllMenuAdapter.ViewHolder>() {

    var menuList: MutableList<MenuView> by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        menuList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHolder(menuList[position], clickListener)
    }

    override fun getItemCount(): Int = menuList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindHolder(menu: MenuView, clickListener: (MenuView) -> Unit) {
            itemView.tvMenuName.text = menu.name
            itemView.tvCountOfPeopleValue.text = menu.peopleCount.toString()
            itemView.tvMenuDateStartValue.text = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(menu.dateOfStartMenu)
            itemView.tvRelaxDayCountValue.text = menu.relaxDayCount.toString()
            itemView.tvStartMenuValue.text = menu.startFrom.title
            itemView.tvCountOfReceptionsValue.text = menu.countOfReceptions.toString()
            itemView.setOnClickListener { clickListener(menu) }
        }
    }
}