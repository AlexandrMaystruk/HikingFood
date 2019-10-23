package com.gmail.maystruks08.domain.entity

import java.util.*

class RelaxDay(
    number: Int,
    date: Date,
    products: MutableMap<TypeOfMeal, MutableList<Product>> = mutableMapOf(),
    weightTotalsForOne: MutableMap<TypeOfMeal, Int> = mutableMapOf(),
    weightTotalsForAll: MutableMap<TypeOfMeal, Int> = mutableMapOf()
) : Day(number, date, products, weightTotalsForOne, weightTotalsForAll)