package com.gmail.maystruks08.hikingfood.core.navigation

import com.gmail.maystruks08.domain.entity.Menu
import com.gmail.maystruks08.hikingfood.ui.createmenu.CreateMenuFragment
import com.gmail.maystruks08.hikingfood.ui.createmenu.createreception.CreateFoodReceptionFragment
import com.gmail.maystruks08.hikingfood.ui.portion.ProductPortionForOnePeopleFragment
import com.gmail.maystruks08.hikingfood.ui.menu.MenuFragment
import com.gmail.maystruks08.hikingfood.ui.main.AllMenuFragment

object Screens {

    class AllMenuScreen : AppScreen() {
        override fun getFragment() = AllMenuFragment.getInstance()
    }

    class MenuScreen(val menuId: Long) : AppScreen() {
        override fun getFragment() = MenuFragment.getInstance(menuId)
    }

    class CreateMenuScreen : AppScreen() {
        override fun getFragment() = CreateMenuFragment.getInstance()
    }


    class CreateReceptionScreen : AppScreen() {
        override fun getFragment() = CreateFoodReceptionFragment.getInstance()
    }

    class ChangeIngredientPortionScreen : AppScreen() {
        override fun getFragment() = ProductPortionForOnePeopleFragment.getInstance()
    }

    const val SELECT_INGREDIENT_DIALOG = "select_ingredient_dialog"

}

