package com.gmail.maystruks08.hikingfood.core.di.application.main.allmenu

import com.gmail.maystruks08.hikingfood.core.di.application.main.createmenu.CreateMenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.menu.MenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.portion.ProductPortionComponent
import dagger.Subcomponent
import com.gmail.maystruks08.hikingfood.ui.main.AllMenuFragment

@Subcomponent(modules = [AllMenuModule::class])
@AllMenuScope
interface AllMenuComponent {

    fun inject(allMenuFragment: AllMenuFragment)

    fun createMenuComponent(): CreateMenuComponent

    fun menuComponent(): MenuComponent

    fun portionComponent(): ProductPortionComponent
}