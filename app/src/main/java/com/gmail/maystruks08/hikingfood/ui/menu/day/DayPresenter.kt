package com.gmail.maystruks08.hikingfood.ui.menu.day

import com.gmail.maystruks08.data.storage.MenuInfo
import com.gmail.maystruks08.domain.entity.ProductSet
import com.gmail.maystruks08.domain.entity.TypeOfMeal
import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.ui.adapter.toProductView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.DayView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.SetProductView
import javax.inject.Inject

class DayPresenter @Inject constructor(private val menuInfo: MenuInfo) : DayContract.Presenter, BasePresenter<DayContract.View>() {

    private lateinit var dayView: DayView

    override fun bindView(view: DayContract.View, dayView: DayView) {
        super.bindView(view)
        this.dayView = dayView
    }

    override fun initFragment() {
        view?.showDayMeal(
            dayView.breakfastProducts.isNotEmpty(),
            dayView.lunchProducts.isNotEmpty(),
            dayView.dinnerProducts.isNotEmpty()
        )

        if (dayView.breakfastProducts.isNotEmpty()) {
            view?.showBreakfastProducts(
                (dayView.number).toString(),
                "Вес на одного: ${dayView.breakfastTotalWeight}",
                "Вес на всех: ${dayView.breakfastTotalWeightForAll}",
                dayView.breakfastProducts
            )
        }

        if (dayView.lunchProducts.isNotEmpty()) {
            view?.showLunchProducts(
                (dayView.number).toString(),
                "Вес на одного: ${dayView.lunchTotalWeight}",
                "Вес на всех: ${dayView.lunchTotalWeightForAll}",
                dayView.lunchProducts
            )
        }

        if (dayView.dinnerProducts.isNotEmpty()) {
            view?.showDinnerProducts(
                (dayView.number).toString(),
                "Вес на одного: ${dayView.dinnerTotalWeight}",
                "Вес на всех: ${dayView.dinnerTotalWeightForAll}",
                dayView.dinnerProducts
            )
        }
    }

    override fun onProductClicked(productView: ProductView, typeOfMeal: TypeOfMeal) {
        if (productView is SetProductView) {
            val productList = when(typeOfMeal){
                TypeOfMeal.BREAKFAST -> dayView.breakfastProducts
                TypeOfMeal.LUNCH ->  dayView.lunchProducts
                else ->  dayView.dinnerProducts
            }
            val position = productList.indexOfFirst { it.id == productView.id }
            val productSetView = (productList[position] as SetProductView)
            val isProductSelected = productSetView.isSelected
            val updatedProduct = productSetView.apply { isSelected = !isProductSelected }
            productList[position] = updatedProduct
            view?.showProductUpdated(updatedProduct, typeOfMeal)
            val productSet =  menuInfo.menuList.find { it.id == dayView.id }?.getProductById(productView.id) as? ProductSet
            productSet?.products?.let { products ->
                products.forEach {
                    if (isProductSelected) {
                        productList.removeAt(position + 1)
                        view?.showProductRemoved(position + 1, typeOfMeal)
                    } else {
                        productList.add(position + 1, it.toProductView(productSetView.id))
                        view?.showProductInserted(it.toProductView(productSetView.id), typeOfMeal,position + 1)
                    }
                }
            }
        }
    }
}
