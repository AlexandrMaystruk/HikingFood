package com.gmail.maystruks08.hikingfood.core.navigation

import com.gmail.maystruks08.hikingfood.ui.createmenu.CreateMenuFragment
import com.gmail.maystruks08.hikingfood.ui.createmenu.createreception.CreateFoodReceptionFragment
import com.gmail.maystruks08.hikingfood.ui.portion.ProductPortionForOnePeopleFragment
import com.gmail.maystruks08.hikingfood.ui.menu.MenuFragment
import com.gmail.maystruks08.hikingfood.ui.main.AllMenuFragment
import com.gmail.maystruks08.hikingfood.ui.menu.day.DayFragment
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView

object Screens {

    class AllMenuScreen : AppScreen() {
        override fun getFragment() = AllMenuFragment.getInstance()
    }

    class MenuScreen(private val menuId: Long) : AppScreen() {
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


    class DayScreen(private val dayView: DayView) : AppScreen() {
        override fun getFragment() = DayFragment.getInstance(dayView)
    }

    const val SELECT_INGREDIENT_DIALOG = "select_ingredient_dialog"

}

