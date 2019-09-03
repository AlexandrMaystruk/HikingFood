package com.gmail.maystruks08.hikingfood.core.base

import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<T : IView> :
    IPresenter<T> {

    override var view: T? = null
    override val compositeDisposable = CompositeDisposable()

    override fun bindView(view: T) {
        this.view = view
        view.configToolbar()
    }

    fun showBaseError(t: Throwable) {
        view?.hideLoading()
        view?.showError(t)
        t.printStackTrace()
    }

    override fun end() {
        view = null
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }

}