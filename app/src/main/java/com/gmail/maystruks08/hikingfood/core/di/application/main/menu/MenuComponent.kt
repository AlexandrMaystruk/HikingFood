package com.gmail.maystruks08.hikingfood.core.di.application.main.menu

import com.gmail.maystruks08.hikingfood.core.di.application.main.day.DayComponent
import dagger.Subcomponent
import com.gmail.maystruks08.hikingfood.ui.main.menu.MenuFragment

@Subcomponent(modules = [MenuModule::class])
@MenuScope
interface MenuComponent {

    fun inject(menuFragment: MenuFragment)

    fun dayComponent(): DayComponent

}