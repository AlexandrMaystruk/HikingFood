package com.gmail.maystruks08.hikingfood.ui.createmenu.createreception.selectingredient

import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView

interface SelectIngredientListener {

    fun onIngredientsSelected(products: List<ProductView>)
}
