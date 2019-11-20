package com.gmail.maystruks08.domain.entity

enum class TypeOfMeal(val type: Int, val title: String) {
    BREAKFAST(0, "Завтрак"),
    LUNCH(1,"Обед"),
    DINNER(2,"Ужин");

    companion object {

        fun fromValue(value: Int, shift: Int = 0): TypeOfMeal {
            val position = (value + shift) % 3
            return values().firstOrNull { it.type == position }?:BREAKFAST
        }

        fun getNextValue(type: Int): TypeOfMeal{
            val nextPosition = type  + 1
            return if(nextPosition > 2){
                BREAKFAST
            } else {
                fromValue(nextPosition)
            }

        }
    }
}