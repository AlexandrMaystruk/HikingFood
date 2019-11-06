package com.gmail.maystruks08.hikingfood.ui.main.menu.purchase

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.ui.viewmodel.PurchaseListItemView
import kotlinx.android.synthetic.main.item_purchase_list.view.*
import kotlin.properties.Delegates

class PurchaseListItemAdapter(private val clickListener: (PurchaseListItemView) -> Unit) :
    RecyclerView.Adapter<PurchaseListItemAdapter.ViewHolder>() {

    var purchaseListItems: MutableList<PurchaseListItemView> by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_purchase_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHolder(purchaseListItems[holder.adapterPosition], clickListener)
    }

    override fun getItemCount(): Int = purchaseListItems.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindHolder(item: PurchaseListItemView, clickListener: (PurchaseListItemView) -> Unit) {
            itemView.tvProductName.text = item.name
            itemView.tvTotalWeight.text = item.totalWeight.toString()
            itemView.tvUnit.text = item.unit.type
            itemView.tvPosition.isChecked = item.isPurchased
            itemView.setOnClickListener {
                val index = purchaseListItems.indexOf(item)
                if (index != -1){
                    purchaseListItems[index].isPurchased = !item.isPurchased
                    itemView.tvPosition.isChecked = !itemView.tvPosition.isChecked
                }
                clickListener(item)
            }
        }
    }
}