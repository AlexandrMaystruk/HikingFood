package com.gmail.maystruks08.hikingfood.ui.viewmodel.mappers

import com.gmail.maystruks08.domain.entity.Product
import com.gmail.maystruks08.domain.entity.ProductSet
import com.gmail.maystruks08.hikingfood.ui.viewmodel.ProductView
import com.gmail.maystruks08.hikingfood.ui.viewmodel.SetProductView
import javax.inject.Inject

class ProductViewMapper @Inject constructor() {

    private fun fromProduct(pr: Product, isChild: Boolean = false): ProductView =
        when (pr) {
            is ProductSet -> {
                SetProductView(
                    id = pr.id,
                    name = pr.name,
                    portionForOnePeople = pr.portion.value,
                    portionForAllPeople = pr.portion.portionForAllPeople,
                    unit = pr.portion.unit,
                    products = pr.products.map { fromProduct(it, true) }
                )
            }
            else -> ProductView(
                id = pr.id,
                name = pr.name,
                portionForOnePeople = pr.portion.value,
                portionForAllPeople = pr.portion.portionForAllPeople,
                unit = pr.portion.unit,
                isChild = isChild,
                isSelected = false
            )
        }


    fun fromProducts(products: List<Product>): MutableList<ProductView> {
        return products.map { fromProduct(it) }.toMutableList()
    }
}