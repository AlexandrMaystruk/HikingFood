package com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception.selectingredient

import com.gmail.maystruks08.hikingfood.core.base.IPresenter
import com.gmail.maystruks08.hikingfood.core.base.IView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductView
import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductViewSelectable

interface AddProductContract {

    interface View : IView {

        fun showProducts(products: List<ProductViewSelectable>)

        fun showProductUpdated(updatedProduct: ProductViewSelectable)

    }


    interface Presenter : IPresenter<View> {

        fun bindView(view: View, products: List<ProductView>)

        fun onProductClicked(productView: ProductViewSelectable)

    }
}
