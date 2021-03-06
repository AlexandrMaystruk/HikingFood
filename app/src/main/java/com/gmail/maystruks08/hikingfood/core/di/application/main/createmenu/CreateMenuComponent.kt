package com.gmail.maystruks08.hikingfood.core.di.application.main.createmenu

import com.gmail.maystruks08.hikingfood.core.di.application.main.createreception.CreateReceptionComponent
import com.gmail.maystruks08.hikingfood.ui.menu.createmenu.CreateMenuFragment
import dagger.Subcomponent

@Subcomponent(modules = [CreateMenuModule::class])
@CreateMenuScope
interface CreateMenuComponent {

    fun inject(createMenuFragment: CreateMenuFragment)

    fun createReceptionComponent(): CreateReceptionComponent

}