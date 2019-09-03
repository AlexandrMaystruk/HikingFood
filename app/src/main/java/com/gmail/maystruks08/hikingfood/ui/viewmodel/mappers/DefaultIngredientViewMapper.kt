package com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers

import com.gmail.maystruks08.domain.entity.Ingredient
import com.gmail.maystruks08.hikingfood.ui.viewmodel.DefaultIngredientView
import javax.inject.Inject

class DefaultIngredientViewMapper @Inject constructor() {

    fun fromIngredient(ingredient: Ingredient): DefaultIngredientView =
        ingredient.let {
            DefaultIngredientView(
                it.product.id.toString(),
                it.product.name,
                it.product.portionForOnePeople,
                it.portionForAllPeople,
                it.unit,
                false
            )
        }

}