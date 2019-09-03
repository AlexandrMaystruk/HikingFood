package com.gmail.maystruks08.hikingfood.core.di.application.menu

import com.gmail.maystruks08.data.repository.MenuRepositoryImpl
import com.gmail.maystruks08.domain.interactor.menu.MenuInteractor
import com.gmail.maystruks08.domain.interactor.menu.MenuInteractorImpl
import com.gmail.maystruks08.domain.repository.MenuRepository
import dagger.Module
import com.gmail.maystruks08.hikingfood.ui.menu.MenuContract
import dagger.Provides
import com.gmail.maystruks08.hikingfood.ui.menu.MenuPresenter

@Module
class MenuModule {


    @Provides
    @MenuScope
    fun interactor(menuInteractorImpl: MenuInteractorImpl): MenuInteractor = menuInteractorImpl

    @Provides
    @MenuScope
    fun presenter(menuPresenter: MenuPresenter): MenuContract.Presenter = menuPresenter

    @Provides
    @MenuScope
    fun userRepository(menuRepositoryImpl: MenuRepositoryImpl): MenuRepository = menuRepositoryImpl

}