package com.gmail.maystruks08.hikingfood.ui.main

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.maystruks08.domain.entity.Menu
import com.gmail.maystruks08.hikingfood.R

import kotlin.properties.Delegates


class AllMenuAdapter(private val clickListener: (Menu) -> Unit) : RecyclerView.Adapter<AllMenuAdapter.ViewHolder>() {

    var menuList: MutableList<Menu> by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        menuList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_menu, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHolder(menuList[position], clickListener)
    }

    override fun getItemCount(): Int = menuList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindHolder(menu: Menu, clickListener: (Menu) -> Unit) {
            itemView.setOnClickListener { clickListener(menu) }
        }
    }
}