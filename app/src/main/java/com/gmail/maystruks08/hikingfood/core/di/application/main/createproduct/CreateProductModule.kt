package com.gmail.maystruks08.hikingfood.core.di.application.main.createproduct

import com.gmail.maystruks08.data.repository.CreateProductRepositoryImpl
import com.gmail.maystruks08.domain.interactor.createmenu.createproduct.CreateProductInteractor
import com.gmail.maystruks08.domain.interactor.createmenu.createproduct.CreateProductInteractorImpl
import com.gmail.maystruks08.domain.repository.CreateProductRepository
import com.gmail.maystruks08.hikingfood.ui.menu.createproduct.CreateProductContract
import com.gmail.maystruks08.hikingfood.ui.menu.createproduct.CreateProductPresenter
import dagger.Module
import dagger.Provides

@Module
class CreateProductModule {

    @Provides
    @CreateProductScope
    fun interactor(impl: CreateProductInteractorImpl): CreateProductInteractor = impl

    @Provides
    @CreateProductScope
    fun presenter(presenter: CreateProductPresenter): CreateProductContract.Presenter = presenter

    @Provides
    @CreateProductScope
    fun repository(impl: CreateProductRepositoryImpl): CreateProductRepository = impl

}