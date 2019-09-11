package com.gmail.maystruks08.hikingfood.ui.menu.day

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView
import kotlinx.android.synthetic.main.item_product.view.*
import kotlin.properties.Delegates

class ProductAdapter(private val clickListener: (ProductView) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var productList: MutableList<ProductView> by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        productList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHolder(productList[position],position % 2 == 0, clickListener)
    }

    override fun getItemCount(): Int = productList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindHolder(product: ProductView, lightBg: Boolean, clickListener: (ProductView) -> Unit) {
            itemView.tvProductName.text = product.name
            itemView.tvIngredientWeightForOnePeople.text =  product.portionForOnePeople.toString()
            itemView.tvIngredientWeightForAllPeople.text = product.portionForAllPeople.toString()
            itemView.background = if (lightBg) {
                ContextCompat.getDrawable(itemView.context, R.drawable.bg_item_dark)
            } else {
                ContextCompat.getDrawable(itemView.context, R.drawable.bg_item_light)
            }
            itemView.setOnClickListener {
                clickListener(product)
            }
        }
    }
}