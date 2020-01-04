package com.gmail.maystruks08.hikingfood.ui.menu.portion

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductPortionView

interface PortionContract {

    interface View : IView {

        fun showProductPortionList(products: List<ProductPortionView>)
    }

    interface Presenter : IPresenter<View> {

        fun onPortionValueChanged(newValue: Int, productId: Long)

        fun onSearchQueryChanged(productName: String)

        fun onNexStepClicked()
    }
}
