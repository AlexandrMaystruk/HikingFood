package com.gmail.maystruks08.hikingfood.core.di.application.main.createproduct

import com.gmail.maystruks08.hikingfood.ui.menu.createproduct.CreateProductFragment
import dagger.Subcomponent

@Subcomponent(modules = [CreateProductModule::class])
@CreateProductScope
interface CreateProductComponent {

    fun inject(fragment: CreateProductFragment)

}