package com.gmail.maystruks08.hikingfood.core.base.adapter

import android.view.View
import com.gmail.maystruks08.hikingfood.ui.viewmodels.*

interface TypesFactory {

    fun type(productPortion: ProductPortionView): Int

    fun type(product: ProductView): Int
    fun type(productSet: SetProductView): Int

    fun type(menu: MenuView): Int

    fun type(day: DayView): Int

    fun type(shoppingListItem: ShoppingListItemView): Int

    fun holder(type: Int, view: View): BaseViewHolder<*>
}