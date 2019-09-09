package com.gmail.maystruks08.domain.entity

data class Portion(var value: Int, val min: Int, val max: Int){

    var portionForAllPeople: Int = value
}