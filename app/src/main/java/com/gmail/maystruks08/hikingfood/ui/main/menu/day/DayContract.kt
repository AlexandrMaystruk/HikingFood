package com.gmail.maystruks08.hikingfood.ui.main.menu.day

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView


interface DayContract {

    interface View : IView {

        fun showBreakfastProducts(number: String,  totalWeight: String, products: MutableList<ProductView>)
        fun showLunchProducts(number: String,  totalWeight: String, products: MutableList<ProductView>)
        fun showDinnerProducts(number: String,  totalWeight: String, products: MutableList<ProductView>)

        fun showBreakfastCard(enable: Boolean)
        fun showLunchCard(enable: Boolean)
        fun showDinnerCard(enable: Boolean)
    }

    interface Presenter : IPresenter<View> {

        fun bindView(view: View, dayView: DayView?)

    }
}
