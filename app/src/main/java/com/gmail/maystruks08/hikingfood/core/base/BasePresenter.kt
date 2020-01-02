package com.gmail.maystruks08.hikingfood.core.base

import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router
import javax.inject.Inject

abstract class BasePresenter<T : IView> : IPresenter<T> {

    @Inject
    lateinit var router: Router
    override var view: T? = null
    override val compositeDisposable = CompositeDisposable()

    override fun bindView(view: T) {
        this.view = view
    }

    override fun onBackClicked(){
        router.exit()
        view = null
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }

    override fun end() {
        view = null
        compositeDisposable.dispose()
        compositeDisposable.clear()
    }

}