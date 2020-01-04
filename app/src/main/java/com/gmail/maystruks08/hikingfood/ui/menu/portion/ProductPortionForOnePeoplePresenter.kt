package com.gmail.maystruks08.hikingfood.ui.menu.portion

import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.domain.interactor.dose.ProductPortionInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.adapter.toProductPortionViewList
import com.gmail.maystruks08.hikingfood.utils.extensions.isolateSpecialSymbolsForRegex
import javax.inject.Inject

class ProductPortionForOnePeoplePresenter @Inject constructor(private val interactor: ProductPortionInteractor) :
    PortionContract.Presenter, BasePresenter<PortionContract.View>() {

    private var products = mutableListOf<Product>()

    override fun bindView(view: PortionContract.View) {
        super.bindView(view)
        view.showLoading()
        compositeDisposable.add(
            interactor.getProducts()
                .doAfterTerminate { view.hideLoading() }
                .subscribe({ productList ->
                    products = productList.toMutableList()
                    view.showProductPortionList(products.toProductPortionViewList())
                }, { it.printStackTrace() })
        )
    }

    override fun onPortionValueChanged(newValue: Int, productId: Long) {
        compositeDisposable.add(
            interactor.onPortionValueChanged(newValue, productId)
                .doOnTerminate {  view?.hideLoading() }
                .subscribe({ }, { it.printStackTrace() })
        )
    }

    /** Filter by product name */
    override fun onSearchQueryChanged(productName: String) {
        if (productName.isEmpty()) {
            view?.showProductPortionList(products.toProductPortionViewList())
        } else {
            view?.showLoading()
            //this pattern use for avoid kotlin crash with regular expression
            val pattern = ".*${productName.isolateSpecialSymbolsForRegex().toLowerCase()}.*".toRegex()
            val filteredProducts = products.filter { pattern.containsMatchIn(it.name.toLowerCase()) }
            view?.showProductPortionList(filteredProducts.toProductPortionViewList())
        }
    }

    override fun onNexStepClicked() {
        router.navigateTo(Screens.CreateReceptionScreen())
    }
}
