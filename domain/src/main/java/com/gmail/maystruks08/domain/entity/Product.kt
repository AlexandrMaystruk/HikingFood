package com.gmail.maystruks08.domain.entity

open class Product(
    val id: Int,
    val name: String,
    var portion: Portion,
    val categories: List<Category>
) {

   open fun calculatePortionForAllPeople(peopleCount: Int) {
        portion.updatePortionForAllPeople(peopleCount)
    }
}