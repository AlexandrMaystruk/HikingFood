package com.gmail.maystruks08.hikingfood.core.di.application.main.createreception.add

import com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception.selectingredient.AddProductContract
import com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception.selectingredient.AddProductsPresenter
import dagger.Module
import dagger.Provides

@Module
class AddProductModule {

    @Provides
    @AddProductScope
    fun presenter(impl: AddProductsPresenter): AddProductContract.Presenter = impl

}