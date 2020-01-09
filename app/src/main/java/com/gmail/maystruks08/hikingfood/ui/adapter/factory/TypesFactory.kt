package com.gmail.maystruks08.hikingfood.ui.adapter.factory

import android.view.View
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks
import com.gmail.maystruks08.hikingfood.ui.adapter.viewholders.BaseViewHolder
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.*

interface TypesFactory {

    fun type(productPortion: ProductPortionView): Int

    fun type(product: ProductView): Int
    fun type(productSet: SetProductView): Int

    fun type(menu: MenuView): Int

    fun type(day: DayView): Int

    fun type(storeDepartmentView: StoreDepartmentView): Int
    fun type(shoppingListItem: ShoppingListItemView): Int

    fun holder(type: Int, view: View, onCLickListenerListener: AdapterCallbacks.OnClickListener<*>?, onItemChangeListenerListener: AdapterCallbacks.OnItemChangeListener<*>?): BaseViewHolder<*>
}