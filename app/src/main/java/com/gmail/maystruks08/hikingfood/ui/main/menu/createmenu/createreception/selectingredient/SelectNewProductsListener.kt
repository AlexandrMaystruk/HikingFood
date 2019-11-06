package com.gmail.maystruks08.hikingfood.ui.main.menu.createmenu.createreception.selectingredient

import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView

interface SelectNewProductsListener {

    fun onStaticProductsSelected(products: List<ProductView>)

    fun onLoopProductsSelected(products: List<ProductView>)
}