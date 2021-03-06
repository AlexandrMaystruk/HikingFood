package com.gmail.maystruks08.hikingfood.ui.menu.shopping

import com.gmail.maystruks08.domain.entity.GroupType
import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.BaseViewModel
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ShoppingListItemView

interface ShoppingListContract {

    interface View : IView {

        fun showShoppingList(items: List<ShoppingListItemView>)

        fun showShoppingListGroupByStoreDepartment(items: List<BaseViewModel>)

        fun showShoppingLisItemUpdated(item: ShoppingListItemView)

        fun showMessage(message: String)
    }

    interface Presenter : IPresenter<View> {

        fun bindView(view: View, menuId: Long)

        fun onSearchQueryChanged(productName: String)

        fun onItemClicked(item: ShoppingListItemView)

        fun onSaveShoppingListToPDF(menuId: Long, menuName: String)

        fun onSelectNewGroupType(newGroupType: GroupType)
    }
}
