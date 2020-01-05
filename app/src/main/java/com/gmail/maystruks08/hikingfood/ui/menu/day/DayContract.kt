package com.gmail.maystruks08.hikingfood.ui.menu.day

import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.DayView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductView

interface DayContract {

    interface View : IView{

        fun showDayMeal(showBreakfast: Boolean,showLunch: Boolean, showDinner: Boolean )

        fun showBreakfastProducts(number: String, totalWeight: String, totalWeightForAll: String, products: MutableList<ProductView>)

        fun showLunchProducts(number: String, totalWeight: String, totalWeightForAll: String, products: MutableList<ProductView>)

        fun showDinnerProducts(number: String, totalWeight: String, totalWeightForAll: String, products: MutableList<ProductView>)

        fun showProductInserted(product: ProductView, typeOfMeal: TypeOfMeal, position: Int? = null)

        fun showProductUpdated(updatedProduct: ProductView, typeOfMeal: TypeOfMeal)

        fun showProductRemoved(position: Int, typeOfMeal: TypeOfMeal)

    }

    interface Presenter : IPresenter<View> {

        fun bindView(view: View, dayView: DayView)

        fun initFragment()

        fun onProductClicked(productView: ProductView, typeOfMeal: TypeOfMeal)

    }
}
