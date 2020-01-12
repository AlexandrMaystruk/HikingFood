package com.gmail.maystruks08.hikingfood.core.di.application.main.createreception.add

import com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception.selectingredient.AddProductsFragment
import dagger.Subcomponent

@Subcomponent(modules = [AddProductModule::class])
@AddProductScope
interface AddProductComponent {

    fun inject(fragment: AddProductsFragment)

}