package com.gmail.maystruks08.hikingfood.core.di.application.main.createreception

import com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu.createreception.CreateFoodReceptionFragment
import dagger.Subcomponent

@Subcomponent(modules = [CreateReceptionModule::class])
@CreateReceptionScope
interface CreateReceptionComponent {

    fun inject(createFoodReceptionFragment: CreateFoodReceptionFragment)

}