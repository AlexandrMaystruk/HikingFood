package com.gmail.maystruks08.domain.entity

import java.util.*

data class Menu(
    val id: Long,
    var name: String,
    var dayCount: Int,
    var restDayCount: Int,
    var dateOfStartMenu: Date,
    var startFrom: TypeOfMeal,
    var defaultProductList: List<Product>,
    var menu: Map<TypeOfMeal, Ingredient>
)