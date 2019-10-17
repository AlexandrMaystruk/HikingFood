package com.gmail.maystruks08.hikingfood.core.di.application.main.menu.day

import dagger.Subcomponent
import com.gmail.maystruks08.hikingfood.ui.main.menu.day.pager.DayPagerFragment

@Subcomponent(modules = [DayModule::class])
@DayScope
interface DayComponent {

    fun inject(dayPagerFragment: DayPagerFragment)

}