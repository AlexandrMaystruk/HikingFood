package com.gmail.maystruks08.hikingfood.ui.main.menu.day.pager

import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.ui.viewmodels.DayView
import javax.inject.Inject

class DayPagerPresenter @Inject constructor() : DayPagerContract.Presenter, BasePresenter<DayPagerContract.View>() {

    override fun bindView(view: DayPagerContract.View, days: List<DayView>, selectedPosition: Int) {
        super.bindView(view)
        view.setupViewPager(days, selectedPosition)
    }
}
