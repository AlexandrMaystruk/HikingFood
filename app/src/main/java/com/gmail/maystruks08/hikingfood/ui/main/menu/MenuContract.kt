package com.gmail.maystruks08.hikingfood.ui.main.menu

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView

interface MenuContract {

    interface View : IView {

        fun showFoodDays(days: List<DayView>)

        fun showMessage(message: String)
    }

    interface Presenter : IPresenter<View> {

        fun initFragment(menuId: Long)

        fun dayItemClicked(day: DayView)

        fun onShowPurchaseList()

        fun onSaveMenuToPDF()
    }
}
