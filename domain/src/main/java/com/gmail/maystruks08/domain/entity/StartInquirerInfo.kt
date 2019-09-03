package com.gmail.maystruks08.domain.entity

import java.util.*

data class StartInquirerInfo(
    var name: String,
    var peopleCount: Int,
    var dayCount: Int,
    var relaxDayCount: Int,
    var dateOfStartMenu: Date,
    var timeOfStartMenu: TypeOfMeal,
    var portionForOnePeople: MutableList<Product>
)

