package com.gmail.maystruks08.hikingfood.core.navigation

import com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu.CreateMenuFragment
import com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu.createreception.CreateFoodReceptionFragment
import com.gmail.maystruks08.hikingfood.ui.main.menu.portion.ProductPortionForOnePeopleFragment
import com.gmail.maystruks08.hikingfood.ui.main.menu.MenuFragment
import com.gmail.maystruks08.hikingfood.ui.main.AllMenuFragment
import com.gmail.maystruks08.hikingfood.ui.main.menu.day.pager.DayPagerFragment
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView

object Screens {

    class AllMenuScreen : AppScreen() {
        override fun getFragment() = AllMenuFragment()
    }

    class MenuScreen(private val menuId: Long) : AppScreen() {
        override fun getFragment() = MenuFragment.getInstance(menuId)
    }

    class CreateMenuScreen : AppScreen() {
        override fun getFragment() = CreateMenuFragment()
    }


    class CreateReceptionScreen : AppScreen() {
        override fun getFragment() = CreateFoodReceptionFragment()
    }

    class ChangeIngredientPortionScreen : AppScreen() {
        override fun getFragment() = ProductPortionForOnePeopleFragment()
    }


    class DayPagerScreen(private val dayViews: List<DayView>, private val position: Int) : AppScreen() {
        override fun getFragment() = DayPagerFragment.getInstance(dayViews, position)
    }

    const val SELECT_PRODUCTS_DIALOG = "SELECT_PRODUCTS_DIALOG"

    const val SELECT_DATE_DIALOG = "SELECT_DATE_DIALOG"


}

