package com.gmail.maystruks08.hikingfood.core.di.application.main.purchase

import com.gmail.maystruks08.data.repository.PurchaseListRepositoryImpl
import com.gmail.maystruks08.domain.interactor.purchaselist.PurchaseListInteractor
import com.gmail.maystruks08.domain.interactor.purchaselist.PurchaseListInteractorImpl
import com.gmail.maystruks08.domain.repository.PurchaseListRepository
import com.gmail.maystruks08.hikingfood.ui.main.menu.purchase.PurchaseListContract
import com.gmail.maystruks08.hikingfood.ui.main.menu.purchase.PurchaseListPresenter
import dagger.Module
import dagger.Provides

@Module
class PurchaseModule {

    @Provides
    @PurchaseListScope
    fun purchaseListInteractor(purchaseListInteractorImpl: PurchaseListInteractorImpl): PurchaseListInteractor = purchaseListInteractorImpl

    @Provides
    @PurchaseListScope
    fun purchaseListPresenter(purchaseListPresenter: PurchaseListPresenter): PurchaseListContract.Presenter = purchaseListPresenter

    @Provides
    @PurchaseListScope
    fun purchaseListRepository(purchaseListRepositoryImpl: PurchaseListRepositoryImpl): PurchaseListRepository = purchaseListRepositoryImpl

}