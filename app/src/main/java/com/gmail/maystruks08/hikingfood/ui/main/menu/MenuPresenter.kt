package com.gmail.maystruks08.hikingfood.ui.main.menu

import com.gmail.maystruks08.domain.entity.Menu
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.domain.interactor.menu.MenuInteractorImpl
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers.DayMenuViewMapper
import com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers.PurchaseListItemViewMapper
import javax.inject.Inject

class MenuPresenter @Inject constructor(
    private val menuInteractorImpl: MenuInteractorImpl,
    private val dayMenuViewMapper: DayMenuViewMapper,
    private val purchaseListItemViewMapper: PurchaseListItemViewMapper) :
    MenuContract.Presenter, BasePresenter<MenuContract.View>() {

    private val actualFoodDays = mutableListOf<DayView>()

    private var menu: Menu? = null

    override fun initFragment(menuId: Long) {
        compositeDisposable.add(
            menuInteractorImpl.getMenu(menuId).subscribe({ menu ->
                this.menu = menu
                actualFoodDays.clear()
                actualFoodDays.addAll(menu.days.map { dayMenuViewMapper.fromMenuDay(menuId, it) })
                view?.showFoodDays(actualFoodDays)
            }, {
                it.printStackTrace()
            })
        )
    }

    override fun onShowPurchaseList() {
        menu?.let {
            router.navigateTo(
                Screens.PurchaseListScreen(purchaseListItemViewMapper.fromPurchaseListItems(it.purchaseList.purchaseListItems))
            )
        }
    }

    override fun dayItemClicked(day: DayView) {
        router.navigateTo(Screens.DayPagerScreen(actualFoodDays, actualFoodDays.indexOf(day)))
    }
}
