package com.gmail.maystruks08.domain.entity

enum class Category(val type: String) {

    PORRIGE("Каша"),
    SOUP("Суп"),
    DRY_LANCH("Сухой обед"),
    SWEET("Сладкое"),
    CAMP("Привальные"),
    ADDITIONALLY("Дополнительно");


    companion object {

        fun fromOrdinal(value: Int): Category {
            return values().firstOrNull { it.ordinal == value }?:PORRIGE
        }
    }
}