package com.gmail.maystruks08.domain.entity

enum class WalkingDayType(val type: Int) {
    NORMAL(0),
    REST(1);

    companion object {

        fun fromValue(value: Int): WalkingDayType {
            return values().firstOrNull { it.type == value }?:NORMAL
        }
    }
}