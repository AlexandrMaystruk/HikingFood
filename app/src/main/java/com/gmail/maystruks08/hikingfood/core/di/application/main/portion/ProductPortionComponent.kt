package com.gmail.maystruks08.hikingfood.core.di.application.main.portion

import com.gmail.maystruks08.hikingfood.ui.menu.portion.ProductPortionForOnePeopleFragment
import dagger.Subcomponent

@Subcomponent(modules = [ProductPortionModule::class])
@ProductPortionScope
interface ProductPortionComponent {

    fun inject(productPortionForOnePeopleFragment: ProductPortionForOnePeopleFragment)

}