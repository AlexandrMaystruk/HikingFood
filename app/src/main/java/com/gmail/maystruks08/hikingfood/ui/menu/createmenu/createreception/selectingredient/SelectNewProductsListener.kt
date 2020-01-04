package com.gmail.maystruks08.hikingfood.ui.menu.createmenu.createreception.selectingredient

import com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels.ProductView

interface SelectNewProductsListener {

    fun onStaticProductsSelected(products: List<ProductView>)

    fun onLoopProductsSelected(products: List<ProductView>)
}
