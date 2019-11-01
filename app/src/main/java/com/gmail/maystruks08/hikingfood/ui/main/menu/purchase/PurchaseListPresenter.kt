package com.gmail.maystruks08.hikingfood.ui.main.menu.purchase

import com.gmail.maystruks08.domain.interactor.purchaselist.PurchaseListInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.ui.viewmodel.PurchaseListItemView
import com.gmail.maystruks08.hikingfood.utils.extensions.isolateSpecialSymbolsForRegex
import javax.inject.Inject

class PurchaseListPresenter @Inject constructor(private val interactor: PurchaseListInteractor) :
    PurchaseListContract.Presenter, BasePresenter<PurchaseListContract.View>() {

    private var items = mutableListOf<PurchaseListItemView>()

    override fun saveInitData(items: MutableList<PurchaseListItemView>) {
        this.items = items
    }

    override fun bindView(view: PurchaseListContract.View) {
        super.bindView(view)
        //TODO if items is empty -> get from db

        view.showPurchaseList(items)
    }

    /** Filter by product name */
    override fun onSearchQueryChanged(productName: String) {
        if (productName.isEmpty()) {
            view?.showPurchaseList(items)
        } else {
            view?.showLoading()
            //this pattern use for avoid kotlin crash with regular expression
            val pattern = ".*${productName.isolateSpecialSymbolsForRegex().toLowerCase()}.*".toRegex()
            val filteredProducts = items.filter { pattern.containsMatchIn(it.name.toLowerCase()) }
            view?.showPurchaseList(filteredProducts)
        }
    }

    override fun onItemClicked(item: PurchaseListItemView) {}
}
