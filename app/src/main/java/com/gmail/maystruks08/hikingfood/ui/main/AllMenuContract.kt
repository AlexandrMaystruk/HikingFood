package com.gmail.maystruks08.hikingfood.ui.main

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.MenuView

interface AllMenuContract {

    interface View : IView {

        fun updateItem(position: Int)

        fun showMenuRemoved(position: Int)

        fun showAllMenuList(allMenuList: List<MenuView>)

        fun showMenuInserted(position: Int, menuView: MenuView)
    }

    interface Presenter : IPresenter<View> {

        fun createNewMenuClicked()

        fun onMenuItemClicked(menuView: MenuView)

        fun onDeleteMenuClicked(position: Int, menuView: MenuView)
    }
}
