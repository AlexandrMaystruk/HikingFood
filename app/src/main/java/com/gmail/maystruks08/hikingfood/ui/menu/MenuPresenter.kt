package com.gmail.maystruks08.hikingfood.ui.menu

import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.domain.interactor.menu.MenuInteractorImpl
import javax.inject.Inject

class MenuPresenter @Inject constructor(private val menuInteractorImpl: MenuInteractorImpl) :
    MenuContract.Presenter, BasePresenter<MenuContract.View>()
