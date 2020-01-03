package com.gmail.maystruks08.hikingfood.core.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import kotlin.properties.Delegates

class Adapter @Inject constructor(
    private val typeFactory: TypesFactory,
    private val clickListener: (ViewModel) -> Unit
) : RecyclerView.Adapter<BaseViewHolder<ViewModel>>() {

    var items: MutableList<ViewModel> by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewModel>, position: Int) {
        holder.bind(items[position], clickListener)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewModel> {
        return typeFactory.holder(
            viewType,
            LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        ) as BaseViewHolder<ViewModel>
    }

    override fun getItemViewType(position: Int): Int = items[position].type(typeFactory)

    override fun getItemCount(): Int = items.count()

}