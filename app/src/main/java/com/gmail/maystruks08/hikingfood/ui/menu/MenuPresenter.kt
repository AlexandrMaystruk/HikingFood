package com.gmail.maystruks08.hikingfood.ui.menu

import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.domain.interactor.menu.MenuInteractorImpl
import com.gmail.maystruks08.hikingfood.core.navigation.Screens
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers.DayMenuViewMapper
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MenuPresenter @Inject constructor(
    private val menuInteractorImpl: MenuInteractorImpl,
    private val dayMenuViewMapper: DayMenuViewMapper,
    private val router: Router
) :
    MenuContract.Presenter, BasePresenter<MenuContract.View>() {
    override fun dayItemClicked(day: DayView) {
        router.navigateTo(Screens.DayScreen(day))
    }

    override fun initFragment(menuId: Long) {
        compositeDisposable.add(
            menuInteractorImpl.getMenuDays(menuId).subscribe({ list ->
                view?.showFoodDays(list.map { dayMenuViewMapper.fromMenuDay(menuId, it) })
            }, {
                it.printStackTrace()
            })
        )
    }
}
