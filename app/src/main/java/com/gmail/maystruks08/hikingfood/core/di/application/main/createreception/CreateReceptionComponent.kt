package com.gmail.maystruks08.hikingfood.core.di.application.main.createreception

import com.gmail.maystruks08.hikingfood.core.di.application.main.createreception.add.AddProductComponent
import com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception.CreateFoodReceptionFragment
import dagger.Subcomponent

@Subcomponent(modules = [CreateReceptionModule::class])
@CreateReceptionScope
interface CreateReceptionComponent {

    fun inject(createFoodReceptionFragment: CreateFoodReceptionFragment)

    fun addProductComponent(): AddProductComponent

}