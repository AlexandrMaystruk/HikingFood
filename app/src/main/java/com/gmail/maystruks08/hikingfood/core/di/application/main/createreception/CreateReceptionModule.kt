package com.gmail.maystruks08.hikingfood.core.di.application.main.createreception

import com.gmail.maystruks08.data.repository.CreateReceptionRepositoryImpl
import com.gmail.maystruks08.domain.interactor.createmenu.createreception.CreateReceptionInteractor
import com.gmail.maystruks08.domain.interactor.createmenu.createreception.CreateReceptionInteractorImpl
import com.gmail.maystruks08.domain.repository.CreateReceptionRepository
import com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception.CreateFoodReceptionContract
import com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception.CreateFoodReceptionPresenter
import dagger.Module
import dagger.Provides


@Module
class CreateReceptionModule {

    @Provides
    @CreateReceptionScope
    fun interactor(createReceptionInteractorImpl: CreateReceptionInteractorImpl): CreateReceptionInteractor = createReceptionInteractorImpl

    @Provides
    @CreateReceptionScope
    fun presenter(createFoodReceptionPresenter: CreateFoodReceptionPresenter): CreateFoodReceptionContract.Presenter = createFoodReceptionPresenter

    @Provides
    @CreateReceptionScope
    fun repository(createReceptionRepositoryImpl: CreateReceptionRepositoryImpl): CreateReceptionRepository = createReceptionRepositoryImpl

}