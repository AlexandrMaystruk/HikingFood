package com.gmail.maystruks08.hikingfood.ui.main.menu.purchase

import android.util.Log
import com.gmail.maystruks08.domain.entity.PurchaseList
import com.gmail.maystruks08.domain.interactor.purchaselist.PurchaseListInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.ui.viewmodel.PurchaseListItemView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers.PurchaseListItemViewMapper
import com.gmail.maystruks08.hikingfood.utils.extensions.isolateSpecialSymbolsForRegex
import javax.inject.Inject

class PurchaseListPresenter @Inject constructor(
    private val purchaseListItemViewMapper: PurchaseListItemViewMapper,
    private val interactor: PurchaseListInteractor
) :
    PurchaseListContract.Presenter, BasePresenter<PurchaseListContract.View>() {

    private var purchaseList: PurchaseList? = null

    private var items = mutableListOf<PurchaseListItemView>()

    override fun bindView(view: PurchaseListContract.View, menuId: Long) {
        super.bindView(view)
        compositeDisposable.add(
            interactor.getPurchaseList(menuId).subscribe({
                purchaseList = it
                items = purchaseListItemViewMapper.fromPurchaseListItems(it.purchaseListItems)
                view.showPurchaseList(items)
            }, {
                it.printStackTrace()
            })
        )
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

    override fun onSavePurchaseListToPDF(menuName: String) {
        purchaseList?.let {
            compositeDisposable.add(
                interactor.exportDataToPDF(menuName, it)
                    .subscribe(::onExportDataToPDFSuccess, ::onExportDataToPDFError)

            )
        }
    }

    private fun onExportDataToPDFSuccess() {
        view?.showMessage("Экспорт файла завершен")
        //TODO show notification
    }

    private fun onExportDataToPDFError(t: Throwable) {
        t.printStackTrace()
        view?.showError(t)

        Log.d("TAGG", "onExportDataToPDFError")

    }
}
