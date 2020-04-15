package com.gmail.maystruks08.hikingfood.ui.menu.createproduct

import com.gmail.maystruks08.domain.CalendarHelper
import com.gmail.maystruks08.domain.interactor.createmenu.createproduct.CreateProductInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import javax.inject.Inject

class CreateProductPresenter @Inject constructor(
    private val interactor: CreateProductInteractor,
    private val calendarHelper: CalendarHelper
) : CreateProductContract.Presenter, BasePresenter<CreateProductContract.View>() {

    override fun createNewProductClicked() {

    }


}
