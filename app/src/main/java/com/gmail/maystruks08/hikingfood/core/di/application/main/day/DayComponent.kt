package com.gmail.maystruks08.hikingfood.core.di.application.main.day

import com.gmail.maystruks08.hikingfood.ui.menu.day.DayFragment
import dagger.Subcomponent
import com.gmail.maystruks08.hikingfood.ui.menu.day.pager.DayPagerFragment

@Subcomponent(modules = [DayModule::class])
@DayScope
interface DayComponent {

    fun inject(dayPagerFragment: DayPagerFragment)

    fun inject(dayFragment: DayFragment)

}