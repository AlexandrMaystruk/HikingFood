package com.gmail.maystruks08.hikingfood.ui.main.menu.day

import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DayView
import javax.inject.Inject

class DayPresenter @Inject constructor() : DayContract.Presenter,
    BasePresenter<DayContract.View>() {

    override fun bindView(view: DayContract.View, dayView: DayView?) {
        super.bindView(view)

        if (dayView == null) {
            view.run {
                showBreakfastCard(false)
                showLunchCard(false)
                showDinnerCard(false)
            }
        }

        dayView?.let {
            view.run {
                showBreakfastCard(it.breakfastProducts.isNotEmpty())
                showLunchCard(it.lunchProducts.isNotEmpty())
                showDinnerCard(it.dinnerProducts.isNotEmpty())
            }

            if (it.breakfastProducts.isNotEmpty()) {
                view.showBreakfastProducts(
                    (it.number).toString(),
                    it.breakfastTotalWeight.toString(),
                    it.breakfastProducts
                )
            }


            if (it.lunchProducts.isNotEmpty()) {
                view.showLunchProducts(
                    (it.number).toString(),
                    it.lunchTotalWeight.toString(),
                    it.lunchProducts
                )
            }

            if (it.dinnerProducts.isNotEmpty()) {
                view.showDinnerProducts(
                    (it.number).toString(),
                    it.dinnerTotalWeight.toString(),
                    it.dinnerProducts
                )
            }
        }
    }
}
