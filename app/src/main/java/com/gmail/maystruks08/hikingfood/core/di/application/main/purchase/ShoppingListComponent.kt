package com.gmail.maystruks08.hikingfood.core.di.application.main.purchase

import com.gmail.maystruks08.hikingfood.ui.menu.shopping.ShoppingListFragment
import dagger.Subcomponent

@Subcomponent(modules = [ShoppingModule::class])
@PurchaseListScope
interface ShoppingListComponent {

    fun inject(shoppingListFragment: ShoppingListFragment)

}