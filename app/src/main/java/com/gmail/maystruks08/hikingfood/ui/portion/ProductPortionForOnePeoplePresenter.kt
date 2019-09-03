package com.gmail.maystruks08.hikingfood.ui.portion

import com.gmail.maystruks08.domain.interactor.dose.ProductPortionInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import javax.inject.Inject

class ProductPortionForOnePeoplePresenter @Inject constructor(private val interactor: ProductPortionInteractor) :
    PortionContract.Presenter, BasePresenter<PortionContract.View>() {

    override fun bindView(view: PortionContract.View) {
        super.bindView(view)
        view.showLoading()
        compositeDisposable.add(
            interactor.getProducts()
                .subscribe({
                    view.hideLoading()
                    view.showProductPortionList(it)
                }, {
                    view.hideLoading()
                    it.printStackTrace()
                })
        )
    }

    override fun onPortionValueChanged(newValue: Int, productName: String) {
        compositeDisposable.add(
            interactor.onPortionValueChanged(newValue, productName)
                .subscribe({
                    view?.hideLoading()
                }, {
                    view?.hideLoading()
                    it.printStackTrace()
                })
        )
    }
}
