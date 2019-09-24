package com.gmail.maystruks08.hikingfood.ui.main.menu.portion

import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView

interface PortionContract {

    interface View : IView{

        fun showProductPortionList(products: List<Product>)
    }

    interface Presenter : IPresenter<View> {

        fun onPortionValueChanged(newValue: Int, productId: Int)
    }
}
