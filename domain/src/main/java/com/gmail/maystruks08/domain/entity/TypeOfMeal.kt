package com.gmail.maystruks08.domain.entity

enum class TypeOfMeal(val type: Int) {
    BREAKFAST(0),
    LANCH(1),
    DINNER(2);

    companion object {

        fun fromValue(value: Int): TypeOfMeal {
            return values().firstOrNull { it.type == value }?:BREAKFAST
        }
    }
}