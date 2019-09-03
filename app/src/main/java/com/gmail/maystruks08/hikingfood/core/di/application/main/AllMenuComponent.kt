package com.gmail.maystruks08.hikingfood.core.di.application.main

import dagger.Subcomponent
import com.gmail.maystruks08.hikingfood.ui.main.AllMenuFragment

@Subcomponent(modules = [AllMenuModule::class])
@AllMenuScope
interface AllMenuComponent {

    fun inject(allMenuFragment: AllMenuFragment)
}