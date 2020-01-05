package com.gmail.maystruks08.hikingfood.ui.adapter.viewholders

import android.view.View
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.StoreDepartmentView
import kotlinx.android.synthetic.main.item_store_department.view.*

class StoreDepartmentHolder(itemView: View, onCLickListenerListener: AdapterCallbacks.OnClickListener<StoreDepartmentView>?, onItemChangeListenerListener: AdapterCallbacks.OnItemChangeListener<StoreDepartmentView>?) : BaseViewHolder<StoreDepartmentView>(itemView, onCLickListenerListener, onItemChangeListenerListener) {

    override fun bind(item: StoreDepartmentView) {
        itemView.tvStoreDepartmentName.text = item.name
    }
}