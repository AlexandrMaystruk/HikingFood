package com.gmail.maystruks08.domain.entity

enum class Unit(val type: String) {
    GRAM("г"),
    KILOGRAM("кг"),
    COUNT("шт");

    companion object {

        fun fromType(type: String): Unit {
            return values().firstOrNull { it.type == type }?:GRAM
        }
    }
}