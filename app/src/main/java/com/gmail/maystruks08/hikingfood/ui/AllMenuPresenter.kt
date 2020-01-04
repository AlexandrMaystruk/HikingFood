package com.gmail.maystruks08.hikingfood.ui

import com.gmail.maystruks08.domain.interactor.allmenu.AllMenuInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.MenuView
import com.gmail.maystruks08.hikingfood.ui.adapter.toMenuView
import com.gmail.maystruks08.hikingfood.utils.extensions.isolateSpecialSymbolsForRegex
import javax.inject.Inject

class AllMenuPresenter @Inject constructor(
    private val interactor: AllMenuInteractor
) : AllMenuContract.Presenter, BasePresenter<AllMenuContract.View>() {

    private var menuViews = mutableListOf<MenuView>()

    override fun bindView(view: AllMenuContract.View) {
        super.bindView(view)
        view.showLoading()
        compositeDisposable.add(
            interactor.provideAllMenuList()
                .subscribe({ list ->
                    menuViews.clear()
                    menuViews.addAll(list.map { it.toMenuView() })
                    view.showNoData(menuViews.isEmpty())
                    view.showAllMenuList(menuViews)
                    view.hideLoading()
                }, {
                    view.showNoData(menuViews.isEmpty())
                    view.hideLoading()
                    it.printStackTrace()
                })
        )
    }

    override fun createNewMenuClicked() {
        router.navigateTo(Screens.CreateMenuScreen())
    }

    override fun onMenuItemClicked(menuView: MenuView) {
        router.navigateTo(Screens.MenuScreen(menuView.id))
    }

    override fun onDeleteMenuClicked(position: Int, menuId: Long) {
        view?.showMenuRemoved(position)
        val removedItem = menuViews.find { it.id == menuId } ?: return
        compositeDisposable.add(
            interactor.removeMenu(menuId)
                .subscribe({
                    menuViews.remove(removedItem)
                    view?.showNoData(menuViews.isEmpty())
                    view?.hideLoading()
                }, {
                    view?.hideLoading()
                    view?.showMenuInserted(position, removedItem)
                    it.printStackTrace()
                })
        )
    }

    override fun onSearchQueryChanged(menuName: String) {
        if (menuName.isEmpty()) {
            view?.showNoData(menuViews.isEmpty())
            view?.showAllMenuList(menuViews)
        } else {
            view?.showLoading()
            //this pattern use for avoid kotlin crash with regular expression
            val pattern = ".*${menuName.isolateSpecialSymbolsForRegex().toLowerCase()}.*".toRegex()
            val filteredProducts = menuViews.filter { pattern.containsMatchIn(it.name.toLowerCase()) }
            view?.showNoData(filteredProducts.isEmpty())
            view?.showAllMenuList(filteredProducts)
        }
    }
}