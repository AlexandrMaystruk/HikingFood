package com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception.selectingredient

import com.gmail.maystruks08.hikingfood.core.base.BasePresenter
import com.gmail.maystruks08.hikingfood.ui.adapter.toSelectableProductView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductViewSelectable
import javax.inject.Inject

class AddProductsPresenter @Inject constructor() : AddProductContract.Presenter, BasePresenter<AddProductContract.View>() {

    private var products = mutableListOf<ProductViewSelectable>()

    override fun bindView(view: AddProductContract.View, products: List<ProductView>) {
        super.bindView(view)
        this.products = products.map { it.toSelectableProductView() }.toMutableList()
        view.showProducts(this.products)
    }

    override fun onProductClicked(productView: ProductViewSelectable) {
       val index = products.indexOf(productView)
        if(index != -1){
            val isSelected = !productView.productView.isSelected
            products[index].productView.isSelected = isSelected
            view?.showProductUpdated(products[index])
        }
    }
}
