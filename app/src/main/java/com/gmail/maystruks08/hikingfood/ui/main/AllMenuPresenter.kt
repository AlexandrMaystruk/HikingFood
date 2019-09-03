package com.gmail.maystruks08.hikingfood.ui.main

import com.gmail.maystruks08.domain.entity.Menu
import com.gmail.maystruks08.domain.interactor.allmenu.AllMenuInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AllMenuPresenter @Inject constructor(
    private val interactor: AllMenuInteractor,
    private val router: Router
) : AllMenuContract.Presenter, BasePresenter<AllMenuContract.View>() {

    override fun bindView(view: AllMenuContract.View) {
        super.bindView(view)
        view.showLoading()
        compositeDisposable.add(
            interactor.provideAllMenuList()
                .subscribe({
                    view.hideLoading()
                    view.showAllMenuList(it)
                }, {
                    view.hideLoading()
                    it.printStackTrace()
                })
        )
    }

    override fun createNewMenuClicked() {
        router.navigateTo(Screens.CreateMenuScreen())
    }

    override fun onMenuItemClicked(menu: Menu) {
        router.navigateTo(Screens.MenuScreen(menu))
    }

    override fun onDeleteMenuClicked(position: Int, menu: Menu) {
        view?.showMenuRemoved(position)
        compositeDisposable.add(
            interactor.removeMenu(menu)
                .subscribe({
                    view?.hideLoading()
                }, {
                    view?.hideLoading()
                    view?.showMenuInserted(position, menu)
                    it.printStackTrace()
                })
        )
    }
}