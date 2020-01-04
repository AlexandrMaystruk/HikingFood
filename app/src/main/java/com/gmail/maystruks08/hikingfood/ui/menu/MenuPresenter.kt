package com.gmail.maystruks08.hikingfood.ui.menu

import android.util.Log
import com.gmail.maystruks08.domain.entity.Menu
import com.gmail.maystruks08.domain.interactor.menu.MenuInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.DayView
import com.gmail.maystruks08.hikingfood.ui.adapter.toDayView
import javax.inject.Inject

class MenuPresenter @Inject constructor(private val interactor: MenuInteractor) :
    MenuContract.Presenter, BasePresenter<MenuContract.View>() {

    private val actualFoodDays = mutableListOf<DayView>()

    private var menu: Menu? = null

    override fun initFragment(menuId: Long) {
        compositeDisposable.add(
            interactor.getMenu(menuId).subscribe({ menu ->
                this.menu = menu
                actualFoodDays.clear()
                actualFoodDays.addAll(menu.days.map { it.toDayView(menuId) })
                view?.showFoodDays(actualFoodDays)
            }, {
                it.printStackTrace()
            })
        )
    }

    override fun onShowShoppingList() {
        menu?.let { router.navigateTo(Screens.ShoppingListScreen(it.id, it.name)) }
    }

    override fun onSaveMenuToPDF() {
        menu?.let {
            compositeDisposable.add(
                interactor.exportMenuDataToPDF(it)
                    .subscribe(::onExportDataToPDFSuccess, ::onExportDataToPDFError)
            )
        }
    }

    override fun dayItemClicked(day: DayView) {
        router.navigateTo(Screens.DayPagerScreen(actualFoodDays, actualFoodDays.indexOf(day)))
    }

    private fun onExportDataToPDFSuccess() {
        view?.showMessage("Экспорт раскладки в PDF файл завершен")
        //TODO show notification
    }

    private fun onExportDataToPDFError(t: Throwable) {
        t.printStackTrace()
        view?.showError(t)
        Log.d("TAGG", "onExportDataToPDFError")
    }
}
