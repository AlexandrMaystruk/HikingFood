package com.gmail.maystruks08.hikingfood.core.di.application.createmenu.createreception

import com.gmail.maystruks08.hikingfood.ui.createmenu.createreception.CreateFoodReceptionFragment
import dagger.Subcomponent

@Subcomponent(modules = [CreateReceptionModule::class])
@CreateReceptionScope
interface CreateReceptionComponent {

    fun inject(createFoodReceptionFragment: CreateFoodReceptionFragment)

}