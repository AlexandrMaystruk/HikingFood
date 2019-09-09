package com.gmail.maystruks08.domain.entity


class Day(
    val number: Int,
    val products: MutableMap<TypeOfMeal, MutableList<Product>> = mutableMapOf(
        TypeOfMeal.BREAKFAST to mutableListOf(),
        TypeOfMeal.LUNCH to mutableListOf(),
        TypeOfMeal.DINNER to mutableListOf()
    )
)