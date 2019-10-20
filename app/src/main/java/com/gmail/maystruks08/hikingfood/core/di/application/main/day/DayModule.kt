package com.gmail.maystruks08.hikingfood.core.di.application.main.day

import dagger.Module
import dagger.Provides
import com.gmail.maystruks08.hikingfood.ui.main.menu.day.pager.DayPagerContract
import com.gmail.maystruks08.hikingfood.ui.main.menu.day.pager.DayPagerPresenter

@Module
class DayModule {

    @Provides
    @DayScope
    fun presenterDayPager(dayPagerPresenter: DayPagerPresenter): DayPagerContract.Presenter = dayPagerPresenter

}