package com.gmail.maystruks08.hikingfood.ui.menu

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView


interface MenuContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}
