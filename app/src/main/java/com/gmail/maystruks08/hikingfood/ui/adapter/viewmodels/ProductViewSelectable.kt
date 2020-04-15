package com.gmail.maystruks08.hikingfood.ui.adapter.viewmodels

import com.gmail.maystruks08.hikingfood.ui.adapter.factory.TypesFactory

class ProductViewSelectable(val productView: ProductView): BaseViewModel(productView.id) {

    override fun type(typesFactory: TypesFactory): Int = typesFactory.type(this)

}