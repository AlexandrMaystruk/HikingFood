package com.gmail.maystruks08.hikingfood.core.di.application.createmenu.createreception

import com.gmail.maystruks08.data.repository.CreateReceptionRepositoryImpl
import com.gmail.maystruks08.domain.interactor.createmenu.createbreakfast.CreateReceptionInteractor
import com.gmail.maystruks08.domain.interactor.createmenu.createbreakfast.CreateReceptionInteractorImpl
import com.gmail.maystruks08.domain.repository.CreateReceptionRepository
import com.gmail.maystruks08.hikingfood.ui.createmenu.createreception.CreateFoodReceptionContract
import com.gmail.maystruks08.hikingfood.ui.createmenu.createreception.CreateFoodReceptionPresenter
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