package com.gmail.maystruks08.hikingfood.core.base.adapter

import android.view.View
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.core.base.adapter.viewholders.*
import com.gmail.maystruks08.hikingfood.ui.viewmodels.*
import javax.inject.Inject

class TypesFactoryImpl @Inject constructor(): TypesFactory {

    override fun type(menu: MenuView): Int = R.layout.item_menu

    override fun type(day: DayView): Int = R.layout.item_day

    override fun type(product: ProductView): Int = R.layout.item_product

    override fun type(productSet: SetProductView): Int = R.layout.item_product_set

    override fun type(productPortion: ProductPortionView): Int = R.layout.item_product_portion

    override fun type(shoppingListItem: ShoppingListItemView): Int = R.layout.item_shopping_list

    override fun holder(type: Int, view: View): BaseViewHolder<*> {
        return when (type) {
            R.layout.item_menu -> MenuHolder(view)
            R.layout.item_day -> DayHolder(view)
            R.layout.item_product -> ProductHolder(view)
            R.layout.item_product_set -> ProductSetHolder(view)
            R.layout.item_product_portion -> ProductPortionHolder(view)
            R.layout.item_shopping_list -> ShoppingItemHolder(view)
            else -> throw RuntimeException("Illegal view type")
        }
    }
}