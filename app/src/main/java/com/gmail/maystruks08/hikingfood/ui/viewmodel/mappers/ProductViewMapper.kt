package com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers

import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.domain.entity.ProductSet
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.SetProductView
import javax.inject.Inject

class ProductViewMapper @Inject constructor() {

    fun fromProduct(pr: Product): ProductView =
        when (pr) {
            is ProductSet -> {
                SetProductView(
                    id = pr.id,
                    name = pr.name,
                    portionForOnePeople = pr.portion.value,
                    portionForAllPeople = pr.portion.portionForAllPeople,
                    unit = pr.unit,
                    products = pr.products.map { fromProduct(it) }
                )
            }
            else -> ProductView(
                id = pr.id,
                name = pr.name,
                portionForOnePeople = pr.portion.value,
                portionForAllPeople = pr.portion.portionForAllPeople,
                unit = pr.unit,
                isSelected = false
            )
        }
}