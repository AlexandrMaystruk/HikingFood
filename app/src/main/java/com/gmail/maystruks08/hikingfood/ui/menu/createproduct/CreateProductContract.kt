package com.gmail.maystruks08.hikingfood.ui.menu.createproduct

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView

interface CreateProductContract {

    interface View : IView {


    }

    interface Presenter : IPresenter<View> {

        fun createNewProductClicked()

    }
}
