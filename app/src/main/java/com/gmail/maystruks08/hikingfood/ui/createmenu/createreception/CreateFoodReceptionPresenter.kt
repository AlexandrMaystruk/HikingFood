package com.gmail.maystruks08.hikingfood.ui.createmenu.createreception

import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.domain.interactor.createmenu.createbreakfast.CreateReceptionInteractor
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers.DefaultIngredientViewMapper
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class CreateFoodReceptionPresenter @Inject constructor(private val router: Router, private val interactor: CreateReceptionInteractor,
                                                       private val defaultIngredientViewMapper: DefaultIngredientViewMapper) :
    CreateFoodReceptionContract.Presenter, BasePresenter<CreateFoodReceptionContract.View>() {

    override fun initFragment(fromValue: TypeOfMeal) {
        view?.showLoading()
        compositeDisposable.add(
            interactor.getDefaultIngredients()
                .subscribe({ list ->
                    view?.hideLoading()
                    view?.showDefaultMenuIngredient(list.map { defaultIngredientViewMapper.fromIngredient(it) })
                }, {
                    view?.hideLoading()
                    it.printStackTrace()
                })
        )
    }
}
