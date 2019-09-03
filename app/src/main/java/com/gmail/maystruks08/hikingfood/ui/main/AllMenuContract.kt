package com.gmail.maystruks08.hikingfood.ui.main

import com.gmail.maystruks08.domain.entity.Menu
import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import io.reactivex.Completable

interface AllMenuContract {

    interface View : IView {

        fun updateItem(position: Int)

        fun showMenuRemoved(position: Int)

        fun showAllMenuList(allMenuList: List<Menu>)

        fun showMenuInserted(position: Int, menu: Menu)
    }

    interface Presenter : IPresenter<View> {

        fun createNewMenuClicked()

        fun onMenuItemClicked(menu: Menu)

        fun onDeleteMenuClicked(position: Int, menu: Menu)
    }
}
