package com.gmail.maystruks08.hikingfood.ui.menu.day

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView


interface DayContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}
