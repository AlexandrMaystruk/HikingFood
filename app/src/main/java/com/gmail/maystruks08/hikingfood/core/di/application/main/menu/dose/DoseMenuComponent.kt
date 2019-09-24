package com.gmail.maystruks08.hikingfood.core.di.application.main.menu.dose

import com.gmail.maystruks08.hikingfood.ui.main.menu.portion.ProductPortionForOnePeopleFragment
import dagger.Subcomponent

@Subcomponent(modules = [DoseMenuModule::class])
@DoseScope
interface DoseMenuComponent {

    fun inject(productPortionForOnePeopleFragment: ProductPortionForOnePeopleFragment)

}