package com.gmail.maystruks08.hikingfood.core.di.application.main.menu.createmenu

import com.gmail.maystruks08.data.repository.CreateMenuRepositoryImpl
import com.gmail.maystruks08.domain.interactor.createmenu.CreateMenuInteractor
import com.gmail.maystruks08.domain.interactor.createmenu.CreateMenuInteractorImpl
import com.gmail.maystruks08.domain.repository.CreateMenuRepository
import com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu.CreateMenuContract
import com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu.CreateMenuPresenter
import dagger.Module
import dagger.Provides


@Module
class CreateMenuModule {

    @Provides
    @CreateMenuScope
    fun interactor(createMenuInteractorImpl: CreateMenuInteractorImpl): CreateMenuInteractor = createMenuInteractorImpl

    @Provides
    @CreateMenuScope
    fun presenter(createMenuPresenter: CreateMenuPresenter): CreateMenuContract.Presenter = createMenuPresenter

    @Provides
    @CreateMenuScope
    fun userRepository(createMenuRepositoryImpl: CreateMenuRepositoryImpl): CreateMenuRepository = createMenuRepositoryImpl

}