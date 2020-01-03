package com.gmail.maystruks08.hikingfood.core.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: T, clickListener: (T) -> Unit)

}


