package com.gmail.maystruks08.hikingfood.ui.createmenu.createreception

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DefaultIngredientView
import kotlinx.android.synthetic.main.item_ingredient.view.*
import kotlin.properties.Delegates

class IngredientForFoodReceptionAdapter(private val clickListener: (DefaultIngredientView) -> Unit) :
    RecyclerView.Adapter<IngredientForFoodReceptionAdapter.ViewHolder>() {

    var ingredientList: MutableList<DefaultIngredientView> by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        ingredientList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHolder(ingredientList[position], clickListener)
    }

    override fun getItemCount(): Int = ingredientList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindHolder(ingredient: DefaultIngredientView, clickListener: (DefaultIngredientView) -> Unit) {
            itemView.tvProductName.text = ingredient.name
            itemView.tvIngredientWeightForOnePeople.text =  ingredient.portionForOnePeople.toString()
            itemView.tvIngredientWeightForAllPeople.text = ingredient.portionForAllPeople.toString()
            itemView.setOnClickListener { clickListener(ingredient) }
        }
    }
}