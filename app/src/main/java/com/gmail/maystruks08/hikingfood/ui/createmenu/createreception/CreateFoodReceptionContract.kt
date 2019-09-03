package com.gmail.maystruks08.hikingfood.ui.createmenu.createreception

import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DefaultIngredientView

interface CreateFoodReceptionContract {

    interface View : IView {

        fun showDefaultMenuIngredient(ingredients: List<DefaultIngredientView>)
    }

    interface Presenter : IPresenter<View> {

        fun initFragment(fromValue: TypeOfMeal)

    }
}
