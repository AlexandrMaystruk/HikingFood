package com.gmail.maystruks08.hikingfood.core.di.application.main.purchase

import com.gmail.maystruks08.data.repository.ShoppingListRepositoryImpl
import com.gmail.maystruks08.domain.interactor.shoppinglist.ShoppingListInteractor
import com.gmail.maystruks08.domain.interactor.shoppinglist.ShoppingListInteractorImpl
import com.gmail.maystruks08.domain.repository.ShoppingListRepository
import com.gmail.maystruks08.hikingfood.ui.menu.shopping.ShoppingListContract
import com.gmail.maystruks08.hikingfood.ui.menu.shopping.ShoppingListPresenter
import dagger.Module
import dagger.Provides

@Module
class ShoppingModule {

    @Provides
    @PurchaseListScope
    fun shoppingListInteractor(impl: ShoppingListInteractorImpl): ShoppingListInteractor = impl

    @Provides
    @PurchaseListScope
    fun shoppingListPresenter(impl: ShoppingListPresenter): ShoppingListContract.Presenter = impl

    @Provides
    @PurchaseListScope
    fun shoppingListRepository(impl: ShoppingListRepositoryImpl): ShoppingListRepository = impl

}