package com.gmail.maystruks08.hikingfood.ui.menu

import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.domain.interactor.menu.MenuInteractorImpl
import com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers.DayMenuViewMapper
import javax.inject.Inject

class MenuPresenter @Inject constructor(
    private val menuInteractorImpl: MenuInteractorImpl,
    private val dayMenuViewMapper: DayMenuViewMapper
) :
    MenuContract.Presenter, BasePresenter<MenuContract.View>() {

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
