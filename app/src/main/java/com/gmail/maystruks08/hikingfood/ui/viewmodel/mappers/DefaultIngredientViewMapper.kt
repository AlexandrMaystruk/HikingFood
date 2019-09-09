package com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers

import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.domain.entity.SoupSet
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView
import javax.inject.Inject

class DefaultIngredientViewMapper @Inject constructor() {

    fun fromProduct(pr: Product): ProductView =
        ProductView(
            id = pr.id,
            name = pr.name,
            portionForOnePeople = pr.portion.value,
            portionForAllPeople = pr.portion.portionForAllPeople,
            unit = pr.unit,
            isSelected = true,
            isSoupSet = pr is SoupSet
        )
}