package com.gmail.maystruks08.domain.entity

enum class StoreDepartment(val type: String) {

    MEET("Мясной отдел"),
    SAUSAGE("Колбысный отдел"),
    FISH("Рыбный отдел"),
    CONSERVE("Консервы"),
    CEREALS("Отдел с крупами"),
    MILK("Молочный отдел"),
    BREAD("Хлебный отдел"),
    SWEET("Сладкое"),
    DRIEDS_FRUITS ("Сухофрукты"),
    FRUITS("Фрукты"),
    VEGETABLES("Овощи"),
    OTHER("Прочее");

    companion object {

        fun fromOrdinal(value: Int): StoreDepartment {
            return values().firstOrNull { it.ordinal == value }?:OTHER
        }
    }
}