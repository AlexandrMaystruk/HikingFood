package com.gmail.maystruks08.hikingfood.core.di.application.main.menu

import dagger.Subcomponent
import com.gmail.maystruks08.hikingfood.ui.menu.MenuFragment

@Subcomponent(modules = [MenuModule::class])
@MenuScope
interface MenuComponent {

    fun inject(menuFragment: MenuFragment)

}