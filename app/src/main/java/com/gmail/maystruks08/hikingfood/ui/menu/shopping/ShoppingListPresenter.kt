package com.gmail.maystruks08.hikingfood.ui.menu.shopping

import android.util.Log
import com.gmail.maystruks08.domain.entity.GroupType
import com.gmail.maystruks08.domain.interactor.shoppinglist.ShoppingListInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ShoppingListItemView
import com.gmail.maystruks08.hikingfood.ui.adapter.toShoppingListItemViewList
import com.gmail.maystruks08.hikingfood.ui.adapter.toStoreDepartmentView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.BaseViewModel
import com.gmail.maystruks08.hikingfood.utils.extensions.isolateSpecialSymbolsForRegex
import javax.inject.Inject

class ShoppingListPresenter @Inject constructor(
    private val interactor: ShoppingListInteractor
) : ShoppingListContract.Presenter, BasePresenter<ShoppingListContract.View>() {

    private var items = mutableListOf<ShoppingListItemView>()
    private var currentGroupType = GroupType.BY_PRODUCT
    private var menuId = -1L

    override fun bindView(view: ShoppingListContract.View, menuId: Long) {
        super.bindView(view)
        this.menuId = menuId
        provideShoppingList(currentGroupType)
    }

    override fun onSelectNewGroupType(newGroupType: GroupType) {
        currentGroupType = newGroupType
        provideShoppingList(newGroupType)
    }

    private fun provideShoppingList(groupType: GroupType) {
       val shoppingListDisposable =  when (groupType) {
            GroupType.BY_PRODUCT -> {
                    interactor.provideShoppingListGroupByProduct(menuId).subscribe({ list ->
                        view?.showShoppingList(list.toShoppingListItemViewList())
                    }, { it.printStackTrace() })
            }
            GroupType.BY_STORE_DEPARTMENT -> {
                interactor.providePurchaseListGroupByStoreDepartment(menuId).subscribe({
                    val shoppingListGroupByStoreDepartment = mutableListOf<BaseViewModel>().apply {
                        it.forEach { (storeDepartment, itemList) ->
                            this.add(storeDepartment.toStoreDepartmentView())
                            this.addAll(itemList.toShoppingListItemViewList().sortedBy { it.name })
                        }
                    }
                    view?.showShoppingListGroupByStoreDepartment(shoppingListGroupByStoreDepartment)
                }, { it.printStackTrace() })
            }
            else -> {
                interactor.providePurchaseListGroupByProductAndStoreDepartment(menuId).subscribe({
                    val shoppingListGroupByProductAndStoreDepartment = mutableListOf<BaseViewModel>().apply {
                        it.forEach { (storeDepartment, itemList) ->
                            this.add(storeDepartment.toStoreDepartmentView())
                            this.addAll(itemList.toShoppingListItemViewList().sortedBy { it.name })
                        }
                    }
                    view?.showShoppingListGroupByStoreDepartment(shoppingListGroupByProductAndStoreDepartment)
                }, { it.printStackTrace() })
            }
        }
        compositeDisposable.add(shoppingListDisposable)
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

    override fun onItemClicked(item: ShoppingListItemView) {
        //TODO save changes to db and storage
        val updatedItem = item.apply { isPurchased = !isPurchased }
        view?.showShoppingLisItemUpdated(updatedItem)
    }

    override fun onSaveShoppingListToPDF(menuId: Long, menuName: String) {
        compositeDisposable.add(
            interactor.exportDataToPDF(menuId, menuName, currentGroupType)
                .subscribe(::onExportDataToPDFSuccess, ::onExportDataToPDFError)
        )
    }

    private fun onExportDataToPDFSuccess() {
        view?.showMessage("Экспорт файла завершен")
    }

    private fun onExportDataToPDFError(t: Throwable) {
        t.printStackTrace()
        view?.showError(t)
        Log.d("TAGG", "onExportDataToPDFError")
    }
}
