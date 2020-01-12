package com.gmail.maystruks08.hikingfood.ui.menu

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.DayView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.MenuView

interface MenuContract {

    interface View : IView {

        fun showFoodDays(days: List<DayView>)

        fun showMenuInfo(menuView: MenuView)

        fun showMessage(message: String)
    }

    interface Presenter : IPresenter<View> {

        fun initFragment(menuId: Long)

        fun dayItemClicked(day: DayView)

        fun onShowShoppingList()

        fun onSaveMenuToPDF()
    }
}
