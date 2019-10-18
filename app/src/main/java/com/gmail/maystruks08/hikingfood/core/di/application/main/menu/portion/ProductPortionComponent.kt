package com.gmail.maystruks08.hikingfood.core.di.application.main.menu.portion

import com.gmail.maystruks08.hikingfood.ui.main.menu.portion.ProductPortionForOnePeopleFragment
import dagger.Subcomponent

@Subcomponent(modules = [ProductPortionModule::class])
@ProductPortionScope
interface ProductPortionComponent {

    fun inject(productPortionForOnePeopleFragment: ProductPortionForOnePeopleFragment)

}