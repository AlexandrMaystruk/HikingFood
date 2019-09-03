package com.gmail.maystruks08.hikingfood.core.di.application.createmenu

import com.gmail.maystruks08.hikingfood.ui.createmenu.CreateMenuFragment
import dagger.Subcomponent

@Subcomponent(modules = [CreateMenuModule::class])
@CreateMenuScope
interface CreateMenuComponent {

    fun inject(createMenuFragment: CreateMenuFragment)

}