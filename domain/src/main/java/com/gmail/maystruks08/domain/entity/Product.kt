package com.gmail.maystruks08.domain.entity

open class Product(
    val id: Long,
    val name: String,
    var portion: Portion,
    val storeDepartment: StoreDepartment ? = null,
    val unit: Unit = Unit.GRAM
) {

   open fun calculatePortionForAllPeople(peopleCount: Int) {
        portion.updatePortionForAllPeople(peopleCount)
    }
}