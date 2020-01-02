package com.gmail.maystruks08.hikingfood.ui.main.menu.shopping

import android.util.Log
import com.gmail.maystruks08.domain.entity.GroupType
import com.gmail.maystruks08.domain.interactor.shoppinglist.ShoppingListInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ShoppingListItemView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers.ShoppingListItemViewMapper
import com.gmail.maystruks08.hikingfood.utils.extensions.isolateSpecialSymbolsForRegex
import javax.inject.Inject

class ShoppingListPresenter @Inject constructor(
    private val shoppingListItemViewMapper: ShoppingListItemViewMapper,
    private val interactor: ShoppingListInteractor
) :
    ShoppingListContract.Presenter, BasePresenter<ShoppingListContract.View>() {

    private var items = mutableListOf<ShoppingListItemView>()

    override fun bindView(view: ShoppingListContract.View, menuId: Long) {
        super.bindView(view)
        compositeDisposable.add(
            interactor.provideShoppingListGroupByProduct(menuId).subscribe({
                items = shoppingListItemViewMapper.fromShoppingListItems(it)
                view.showShoppingList(items)
            }, {
                it.printStackTrace()
            })
        )
    }

    /** Filter by product name */
    override fun onSearchQueryChanged(productName: String) {
        if (productName.isEmpty()) {
            view?.showShoppingList(items)
        } else {
            view?.showLoading()
            //this pattern use for avoid kotlin crash with regular expression
            val pattern = ".*${productName.isolateSpecialSymbolsForRegex().toLowerCase()}.*".toRegex()
            val filteredProducts = items.filter { pattern.containsMatchIn(it.name.toLowerCase()) }
            view?.showShoppingList(filteredProducts)
        }
    }

    override fun onItemClicked(item: ShoppingListItemView) {}

    override fun onSaveShoppingListToPDF(menuId: Long, menuName: String) {
        val hardCodeType = GroupType.BY_STORE_DEPARTMENT
        compositeDisposable.add(
            interactor.exportDataToPDF(menuId, menuName, hardCodeType)
                .subscribe(::onExportDataToPDFSuccess, ::onExportDataToPDFError)
        )
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
