package com.gmail.maystruks08.hikingfood.ui.main.menu

import android.graphics.Typeface
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.ui.viewmodels.ProductView
import com.gmail.maystruks08.hikingfood.ui.viewmodels.SetProductView
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_product.view.ivProductSetIcon
import kotlinx.android.synthetic.main.item_product.view.tvProductName
import kotlinx.android.synthetic.main.item_product.view.tvProductWeightForAllPeople
import kotlinx.android.synthetic.main.item_product.view.tvProductWeightForOnePeople
import kotlin.properties.Delegates

class ProductAdapter(private var hasCheckbox: Boolean = false, private val clickListener: (ProductView) -> Unit) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var productList: MutableList<ProductView> by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun getSelectedItems() = productList.filter { it.isSelected }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHolder(productList[holder.adapterPosition], holder.adapterPosition, clickListener)
    }

    override fun getItemCount(): Int = productList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindHolder(product: ProductView, position: Int, clickListener: (ProductView) -> Unit) {
            itemView.setOnLongClickListener {
                hasCheckbox = true
                true
            }
            itemView.tvProductName.text = product.name
            itemView.tvProductWeightForOnePeople.text = product.portionForOnePeople.toString()
            itemView.tvProductWeightForAllPeople.text = product.portionForAllPeople.toString()
            itemView.cbSelectedState.visibility = if (hasCheckbox && !product.isChild) {
                itemView.tvPosition.visibility = View.GONE
                itemView.cbSelectedState.isChecked = product.isSelected
                View.VISIBLE
            } else {
                itemView.tvPosition.visibility = View.VISIBLE
                View.GONE
            }

            itemView.background = if (product is SetProductView) {
                itemView.tvPosition.text = (position + 1).toString()
                itemView.ivProductSetIcon.visibility = View.VISIBLE
                itemView.tvProductName.setTypeface(null, Typeface.BOLD)
                itemView.tvProductWeightForOnePeople.setTypeface(null, Typeface.BOLD)
                itemView.tvProductWeightForAllPeople.setTypeface(null, Typeface.BOLD)
                if (product.isSelected) {
                    itemView.ivProductSetIcon.setImageDrawable(
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_expand_more
                        )
                    )
                } else {
                    itemView.ivProductSetIcon.setImageDrawable(
                        ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_arrow_right
                        )
                    )
                }

                ContextCompat.getDrawable(itemView.context, R.drawable.bg_item_set)
            } else {
                itemView.tvPosition.text = ""
                itemView.ivProductSetIcon.visibility = View.GONE
                if (product.isChild) {
                    itemView.tvProductName.setTypeface(null, Typeface.ITALIC)
                    itemView.tvProductWeightForOnePeople.setTypeface(null, Typeface.ITALIC)
                    itemView.tvProductWeightForAllPeople.setTypeface(null, Typeface.ITALIC)
                    ContextCompat.getDrawable(itemView.context, R.drawable.bg_item_set_child)
                } else {
                    itemView.tvProductName.typeface = Typeface.DEFAULT
                    itemView.tvProductWeightForOnePeople.typeface = Typeface.DEFAULT
                    itemView.tvProductWeightForAllPeople.typeface = Typeface.DEFAULT
                    itemView.tvPosition.text = (position + 1).toString()
                    if (position % 2 == 0) {
                        ContextCompat.getDrawable(itemView.context, R.drawable.bg_item_light)
                    } else {
                        ContextCompat.getDrawable(itemView.context, R.drawable.bg_item_dark)
                    }
                }
            }

            if (product is SetProductView) {
                itemView.setOnClickListener {
                    productList[position].isSelected = !product.isSelected
                    if (product.isSelected) {
                        productList.addAll(position + 1, product.products)
                    } else {
                        productList.removeAll(product.products)
                    }
                    notifyDataSetChanged()
                }
            } else {
                itemView.setOnClickListener {
                    if (hasCheckbox && !product.isChild) {
                        val state = !product.isSelected
                        itemView.cbSelectedState.isChecked = state
                        productList[position].isSelected = state
                    }
                    clickListener(product)
                }
            }
        }
    }
}