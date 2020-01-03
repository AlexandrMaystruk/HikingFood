package com.gmail.maystruks08.hikingfood.ui.main.menu.portion

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.viewmodels.ProductPortionView

interface PortionContract {

    interface View : IView {

        fun showProductPortionList(products: List<ProductPortionView>)
    }

    interface Presenter : IPresenter<View> {

        fun onPortionValueChanged(newValue: Int, productId: Int)

        fun onSearchQueryChanged(productName: String)

        fun onNexStepClicked()
    }
}
