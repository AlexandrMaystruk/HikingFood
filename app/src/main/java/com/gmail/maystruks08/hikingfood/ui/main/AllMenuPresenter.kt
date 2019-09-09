package com.gmail.maystruks08.hikingfood.ui.main

import com.gmail.maystruks08.domain.interactor.allmenu.AllMenuInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.viewmodel.MenuView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers.MenuViewMapper
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AllMenuPresenter @Inject constructor(
    private val interactor: AllMenuInteractor,
    private val router: Router,
    private val menuViewMapper: MenuViewMapper
) : AllMenuContract.Presenter, BasePresenter<AllMenuContract.View>() {

    override fun bindView(view: AllMenuContract.View) {
        super.bindView(view)
        view.showLoading()
        compositeDisposable.add(
            interactor.provideAllMenuList()
                .subscribe({
                    view.hideLoading()
                    view.showAllMenuList(it.map { menuViewMapper.fromMenu(it) })
                }, {
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
                    view?.hideLoading()
                }, {
                    view?.hideLoading()
                    view?.showMenuInserted(position, menuView)
                    it.printStackTrace()
                })
        )
    }
}