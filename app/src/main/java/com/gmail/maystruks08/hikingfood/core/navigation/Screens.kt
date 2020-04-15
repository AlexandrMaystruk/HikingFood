package com.gmail.maystruks08.hikingfood.core.navigation

import com.gmail.maystruks08.hikingfood.ui.menu.createmenu.CreateMenuFragment
import com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception.CreateFoodReceptionFragment
import com.gmail.maystruks08.hikingfood.ui.menu.portion.ProductPortionForOnePeopleFragment
import com.gmail.maystruks08.hikingfood.ui.menu.MenuFragment
import com.gmail.maystruks08.hikingfood.ui.AllMenuFragment
import com.gmail.maystruks08.hikingfood.ui.menu.day.pager.DayPagerFragment
import com.gmail.maystruks08.hikingfood.ui.menu.shopping.ShoppingListFragment
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.DayView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductView
import com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception.selectingredient.AddProductsFragment

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

    class ShoppingListScreen(private val menuId: Long, private val menuName: String) : AppScreen() {
        override fun getFragment() = ShoppingListFragment.getInstance(menuId, menuName)
    }

    class DayPagerScreen(private val dayViews: List<DayView>, private val position: Int) : AppScreen() {
        override fun getFragment() = DayPagerFragment.getInstance(dayViews, position)
    }

    class AddProductsScreen(
        private val products: List<ProductView>,
        private val isDefProducts: Boolean,
        private val callback: (List<ProductView>, Boolean) -> Unit
    ) : AppScreen() {
        override fun getFragment() = AddProductsFragment.getInstance(products, isDefProducts, callback)
    }

    const val SELECT_DATE_DIALOG = "SELECT_DATE_DIALOG"

}

