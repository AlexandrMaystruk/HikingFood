package com.gmail.maystruks08.hikingfood.core.di.application.main.day

import dagger.Module
import dagger.Provides
import com.gmail.maystruks08.hikingfood.ui.menu.day.DayContract
import com.gmail.maystruks08.hikingfood.ui.menu.day.DayPresenter

@Module
class DayModule {

    //I removed @DayScope. It mean that dagger will return new presenter object.
    // This need because I use fragment pager
    @Provides
    fun presenterDayPager(dayPresenter: DayPresenter): DayContract.Presenter = dayPresenter

}