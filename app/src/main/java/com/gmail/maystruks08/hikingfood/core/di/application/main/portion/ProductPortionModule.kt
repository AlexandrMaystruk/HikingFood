package com.gmail.maystruks08.hikingfood.core.di.application.main.portion

import com.gmail.maystruks08.data.repository.ProductPortionRepositoryImpl
import com.gmail.maystruks08.domain.interactor.dose.ProductPortionInteractor
import com.gmail.maystruks08.domain.interactor.dose.ProductPortionInteractorImpl
import com.gmail.maystruks08.domain.repository.ProductPortionRepository
import com.gmail.maystruks08.hikingfood.ui.main.menu.portion.PortionContract
import com.gmail.maystruks08.hikingfood.ui.main.menu.portion.ProductPortionForOnePeoplePresenter
import dagger.Module
import dagger.Provides


@Module
class ProductPortionModule {

    @Provides
    @ProductPortionScope
    fun interactor(doseInteractorImpl: ProductPortionInteractorImpl): ProductPortionInteractor = doseInteractorImpl

    @Provides
    @ProductPortionScope
    fun presenter(productPortionForOnePeoplePresenter: ProductPortionForOnePeoplePresenter): PortionContract.Presenter = productPortionForOnePeoplePresenter

    @Provides
    @ProductPortionScope
    fun userRepository(productPortionRepositoryImpl: ProductPortionRepositoryImpl): ProductPortionRepository = productPortionRepositoryImpl

}