package com.gmail.maystruks08.hikingfood.ui.adapter.factory

import android.view.View
import com.gmail.maystruks08.hikingfood.R
import com.gmail.maystruks08.hikingfood.ui.adapter.AdapterCallbacks
import com.gmail.maystruks08.hikingfood.ui.adapter.viewholders.BaseViewHolder
import com.gmail.maystruks08.hikingfood.ui.adapter.viewholders.*
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.*
import javax.inject.Inject

class TypesFactoryImpl @Inject constructor(): TypesFactory {

    override fun type(menu: MenuView): Int = R.layout.item_menu

    override fun type(day: DayView): Int = R.layout.item_day

    override fun type(product: ProductView): Int = R.layout.item_product

    override fun type(productSet: SetProductView): Int = R.layout.item_product_set

    override fun type(productPortion: ProductPortionView): Int = R.layout.item_product_portion

    override fun type(storeDepartmentView: StoreDepartmentView): Int = R.layout.item_store_department

    override fun type(shoppingListItem: ShoppingListItemView): Int = R.layout.item_shopping_list

    @Suppress("UNCHECKED_CAST")
    override fun holder(type: Int, view: View, onCLickListenerListener: AdapterCallbacks.OnClickListener<*>?, onItemChangeListenerListener: AdapterCallbacks.OnItemChangeListener<*>?): BaseViewHolder<*> {
        return when (type) {
            R.layout.item_menu -> MenuHolder(view, onCLickListenerListener as? AdapterCallbacks.OnClickListener<MenuView>?, onItemChangeListenerListener as? AdapterCallbacks.OnItemChangeListener<MenuView>?)
            R.layout.item_day -> DayHolder(view, onCLickListenerListener as? AdapterCallbacks.OnClickListener<DayView>?, onItemChangeListenerListener as? AdapterCallbacks.OnItemChangeListener<DayView>?)
            R.layout.item_product -> ProductHolder(view, onCLickListenerListener as? AdapterCallbacks.OnClickListener<ProductView>?, onItemChangeListenerListener as? AdapterCallbacks.OnItemChangeListener<ProductView>?)
            R.layout.item_product_set -> ProductSetHolder(view, onCLickListenerListener as? AdapterCallbacks.OnClickListener<SetProductView>?, onItemChangeListenerListener as? AdapterCallbacks.OnItemChangeListener<SetProductView>?)
            R.layout.item_product_portion -> ProductPortionHolder(view, onCLickListenerListener as? AdapterCallbacks.OnClickListener<ProductPortionView>?, onItemChangeListenerListener as? AdapterCallbacks.OnItemChangeListener<ProductPortionView>?)
            R.layout.item_store_department -> StoreDepartmentHolder(view, onCLickListenerListener as? AdapterCallbacks.OnClickListener<StoreDepartmentView>?, onItemChangeListenerListener as? AdapterCallbacks.OnItemChangeListener<StoreDepartmentView>?)
            R.layout.item_shopping_list -> ShoppingItemHolder(view, onCLickListenerListener as? AdapterCallbacks.OnClickListener<ShoppingListItemView>?, onItemChangeListenerListener as? AdapterCallbacks.OnItemChangeListener<ShoppingListItemView>?)
            else -> throw RuntimeException("Illegal view type")
        }
    }
}