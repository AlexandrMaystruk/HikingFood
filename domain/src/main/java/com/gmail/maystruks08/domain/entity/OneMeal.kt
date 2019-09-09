package com.gmail.maystruks08.domain.entity


data class OneMeal(
    var numberOfReception: Int,
    var products: List<Product>,
    val typeOfMeal: TypeOfMeal

)