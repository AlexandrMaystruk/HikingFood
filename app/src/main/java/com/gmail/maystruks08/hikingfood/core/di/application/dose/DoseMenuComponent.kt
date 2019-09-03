package com.gmail.maystruks08.hikingfood.core.di.application.dose

import com.gmail.maystruks08.hikingfood.ui.portion.ProductPortionForOnePeopleFragment
import dagger.Subcomponent

@Subcomponent(modules = [DoseMenuModule::class])
@DoseScope
interface DoseMenuComponent {

    fun inject(productPortionForOnePeopleFragment: ProductPortionForOnePeopleFragment)

}