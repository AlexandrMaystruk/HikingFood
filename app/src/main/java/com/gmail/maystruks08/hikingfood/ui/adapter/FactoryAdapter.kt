package com.gmail.maystruks08.hikingfood.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory
import com.gmail.maystruks08.hikingfood.ui.adapter.viewholders.BaseViewHolder
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.BaseViewModel
import javax.inject.Inject
import kotlin.properties.Delegates

class FactoryAdapter @Inject constructor(
    private val typeFactory: TypesFactory,
    private val onClickListener: AdapterCallbacks.OnClickListener<*>? = null,
    private val onItemChangeListener: AdapterCallbacks.OnItemChangeListener<*>? = null
) : RecyclerView.Adapter<BaseViewHolder<BaseViewModel>>() {

    var items: MutableList<BaseViewModel> by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    fun insertItem(item: BaseViewModel, position: Int? = null) {
        if(position!= null){
            items.add(position, item)
            notifyItemInserted(position)
        } else {
            items.add(item)
            notifyItemInserted(items.indexOf(item))
        }
    }

    fun updateItem(item: BaseViewModel){
        val index = items.indexOfFirst { item.id == it.id }
        if(index == -1) return
        items.removeAt(index)
        items.add(index, item)
        notifyItemChanged(index)
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun removeItems(startPosition: Int, count: Int) {
        repeat(count){ items.removeAt(startPosition) }
        notifyItemRangeRemoved(startPosition, count)
    }
    override fun onBindViewHolder(holder: BaseViewHolder<BaseViewModel>, position: Int) {
        holder.bind(items[position])
    }

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseViewModel> {
        return typeFactory.holder(
            viewType,
            LayoutInflater.from(parent.context).inflate(viewType, parent, false),
            onClickListener,
            onItemChangeListener
        ) as BaseViewHolder<BaseViewModel>
    }

    override fun getItemViewType(position: Int): Int = items[position].type(typeFactory)

    override fun getItemCount(): Int = items.count()

}