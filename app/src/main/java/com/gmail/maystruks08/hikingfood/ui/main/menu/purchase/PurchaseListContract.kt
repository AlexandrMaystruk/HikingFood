package com.gmail.maystruks08.hikingfood.ui.main.menu.purchase

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.PurchaseListItemView

interface PurchaseListContract {

    interface View : IView {

        fun showPurchaseList(items: List<PurchaseListItemView>)
    }

    interface Presenter : IPresenter<View> {

        fun saveInitData(items: MutableList<PurchaseListItemView>)

        fun onSearchQueryChanged(productName: String)

        fun onItemClicked(item: PurchaseListItemView)
    }
}
