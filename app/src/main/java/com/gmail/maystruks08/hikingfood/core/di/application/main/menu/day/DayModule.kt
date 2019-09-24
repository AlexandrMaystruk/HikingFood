package com.gmail.maystruks08.hikingfood.core.di.application.main.menu.day

import dagger.Module
import dagger.Provides
import com.gmail.maystruks08.hikingfood.ui.main.menu.day.DayContract
import com.gmail.maystruks08.hikingfood.ui.main.menu.day.DayPresenter

@Module
class DayModule {

    @Provides
    @DayScope
    fun presenter(dayPresenter: DayPresenter): DayContract.Presenter = dayPresenter

}