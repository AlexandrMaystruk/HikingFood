package com.gmail.maystruks08.hikingfood.ui.portion

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.hikingfood.R
import kotlinx.android.synthetic.main.item_card_product_portion.view.*
import kotlin.properties.Delegates

class ProductsPortionAdapter(private val portionChangeListener: (Int, String) -> Unit) :
    RecyclerView.Adapter<ProductsPortionAdapter.ViewHolder>() {

    var productList: MutableList<Product> by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        productList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card_product_portion, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHolder(productList[position], portionChangeListener)
    }

    override fun getItemCount(): Int = productList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindHolder(product: Product, portionChangeListener: (Int, String) -> Unit) {
            itemView.tvProductName.text = product.name
            itemView.numberPickerProductPortion.minValue = (product.portionForOnePeople * 0.7).toInt()
            itemView.numberPickerProductPortion.maxValue = (product.portionForOnePeople * 1.3).toInt()
            itemView.numberPickerProductPortion.value = product.portionForOnePeople
            itemView.numberPickerProductPortion.wrapSelectorWheel = false

            itemView.numberPickerProductPortion.setOnValueChangedListener { _, _, newVal ->
                portionChangeListener(newVal, product.name)
            }
        }
    }
}