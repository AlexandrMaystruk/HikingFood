package com.gmail.maystruks08.hikingfood.core.di.application.main.allmenu

import com.gmail.maystruks08.data.repository.AllMenuRepositoryImpl
import com.gmail.maystruks08.domain.interactor.allmenu.AllMenuInteractor
import com.gmail.maystruks08.domain.interactor.allmenu.AllMenuInteractorImpl
import com.gmail.maystruks08.domain.repository.AllMenuRepository
import com.gmail.maystruks08.hikingfood.core.base.adapter.TypesFactory
import com.gmail.maystruks08.hikingfood.core.base.adapter.TypesFactoryImpl
import dagger.Module
import dagger.Provides
import com.gmail.maystruks08.hikingfood.ui.main.AllMenuContract
import com.gmail.maystruks08.hikingfood.ui.main.AllMenuPresenter

@Module
class AllMenuModule {

    @Provides
    @AllMenuScope
    fun interactor(allMenuInteractorImpl: AllMenuInteractorImpl): AllMenuInteractor = allMenuInteractorImpl

    @Provides
    @AllMenuScope
    fun presenter(allMenuPresenter: AllMenuPresenter): AllMenuContract.Presenter = allMenuPresenter

    @Provides
    @AllMenuScope
    fun userRepository(allMenuRepositoryImpl: AllMenuRepositoryImpl): AllMenuRepository = allMenuRepositoryImpl

    @Provides
    @AllMenuScope
    fun adapterTypeFactory(impl: TypesFactoryImpl): TypesFactory = impl

}