package com.gmail.maystruks08.hikingfood.core.base

import io.reactivex.disposables.CompositeDisposable

interface IPresenter <T: IView> {

    var view: T?

    val compositeDisposable: CompositeDisposable

    fun bindView(view: T)

    fun onBackClicked()

    fun end ()

}