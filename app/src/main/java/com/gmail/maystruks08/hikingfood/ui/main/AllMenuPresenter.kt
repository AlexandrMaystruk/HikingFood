package com.gmail.maystruks08.hikingfood.ui.main

import com.gmail.maystruks08.domain.interactor.allmenu.AllMenuInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.viewmodel.MenuView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers.MenuViewMapper
import com.gmail.maystruks08.hikingfood.utils.extensions.isolateSpecialSymbolsForRegex
import javax.inject.Inject

class AllMenuPresenter @Inject constructor(
    private val interactor: AllMenuInteractor,
    private val menuViewMapper: MenuViewMapper
) : AllMenuContract.Presenter, BasePresenter<AllMenuContract.View>() {

    private var menuViews = mutableListOf<MenuView>()

    override fun bindView(view: AllMenuContract.View) {
        super.bindView(view)
        view.showLoading()
        compositeDisposable.add(
            interactor.provideAllMenuList()
                .subscribe({ list ->
                    menuViews.clear()
                    menuViews.addAll(list.map { menuViewMapper.fromMenu(it) })
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

    override fun onDeleteMenuClicked(position: Int, menuView: MenuView) {
        view?.showMenuRemoved(position)
        compositeDisposable.add(
            interactor.removeMenu(menuView.id)
                .subscribe({
                    menuViews.remove(menuView)
                    view?.showNoData(menuViews.isEmpty())
                    view?.hideLoading()
                }, {
                    view?.hideLoading()
                    view?.showMenuInserted(position, menuView)
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
            val filteredProducts =
                menuViews.filter { pattern.containsMatchIn(it.name.toLowerCase()) }
            view?.showNoData(filteredProducts.isEmpty())
            view?.showAllMenuList(filteredProducts)
        }
    }
}