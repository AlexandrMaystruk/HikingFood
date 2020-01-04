package com.gmail.maystruks08.hikingfood.ui.adapter.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks

abstract class BaseViewHolder<T>(
    view: View,
    val onCLickListenerListener: AdapterCallbacks.OnClickListener<T>?,
    val onItemChangeListenerListener: AdapterCallbacks.OnItemChangeListener<T>?
) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: T)

}


