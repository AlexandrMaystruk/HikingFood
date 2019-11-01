package com.gmail.maystruks08.domain.entity

enum class Unit(val type: String) {
    GRAM("Гр."),
    KILOGRAM("Кг."),
    COUNT("Шт.");

    companion object {

        fun fromValue(value: String): Unit {
            return values().firstOrNull { it.type == value }?:GRAM
        }
    }
}