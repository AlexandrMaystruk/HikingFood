package com.gmail.maystruks08.hikingfood.core.di.application.main.allmenu

import com.gmail.maystruks08.hikingfood.core.di.application.main.createmenu.CreateMenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.day.DayComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.menu.MenuComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.portion.ProductPortionComponent
import com.gmail.maystruks08.hikingfood.core.di.application.main.purchase.ShoppingListComponent
import dagger.Subcomponent
import com.gmail.maystruks08.hikingfood.ui.AllMenuFragment

@Subcomponent(modules = [AllMenuModule::class])
@AllMenuScope
interface AllMenuComponent {

    fun inject(allMenuFragment: AllMenuFragment)

    fun createMenuComponent(): CreateMenuComponent

    fun menuComponent(): MenuComponent

    fun dayComponent(): DayComponent

    fun purchaseListComponent(): ShoppingListComponent

    fun portionComponent(): ProductPortionComponent
}