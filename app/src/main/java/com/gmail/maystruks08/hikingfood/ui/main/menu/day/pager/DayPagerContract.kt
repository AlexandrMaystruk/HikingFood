package com.gmail.maystruks08.hikingfood.ui.main.menu.day.pager

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView

interface DayPagerContract {

    interface View : IView{

        fun setupViewPager(days: List<DayView>, selectedPosition: Int)

    }

    interface Presenter : IPresenter<View> {

        fun bindView(view: View, days: List<DayView>, selectedPosition: Int)

    }
}
