package com.gmail.maystruks08.hikingfood.ui.main.menu.shopping

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.ui.viewmodels.ShoppingListItemView
import kotlinx.android.synthetic.main.item_shopping_list.view.*
import kotlin.properties.Delegates

class ShoppingListItemAdapter(private val clickListener: (ShoppingListItemView) -> Unit) :
    RecyclerView.Adapter<ShoppingListItemAdapter.ViewHolder>() {

    var shoppingListItems: MutableList<ShoppingListItemView> by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHolder(shoppingListItems[holder.adapterPosition], clickListener)
    }

    override fun getItemCount(): Int = shoppingListItems.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindHolder(item: ShoppingListItemView, clickListener: (ShoppingListItemView) -> Unit) {
            itemView.tvProductName.text = item.name
            itemView.tvTotalWeight.text = item.totalWeight.toString()
            itemView.tvUnit.text = item.unit.type
            itemView.tvPosition.isChecked = item.isPurchased
            itemView.setOnClickListener {
                val index = shoppingListItems.indexOf(item)
                if (index != -1){
                    shoppingListItems[index].isPurchased = !item.isPurchased
                    itemView.tvPosition.isChecked = !itemView.tvPosition.isChecked
                }
                clickListener(item)
            }
        }
    }
}