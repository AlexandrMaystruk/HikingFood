package com.gmail.maystruks08.domain.entity

enum class Unit(val type: String) {
    GRAM("г"),
    KILOGRAM("кг"),
    COUNT("шт");

    companion object {

        fun fromValue(value: String): Unit {
            return values().firstOrNull { it.type == value }?:GRAM
        }
    }
}