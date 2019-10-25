package com.gmail.maystruks08.hikingfood.core.di.application.main.purchase

import com.gmail.maystruks08.hikingfood.ui.main.menu.purchase.PurchaseListFragment
import dagger.Subcomponent

@Subcomponent(modules = [PurchaseModule::class])
@PurchaseListScope
interface PurchaseListComponent {

    fun inject(purchaseListFragment: PurchaseListFragment)

}