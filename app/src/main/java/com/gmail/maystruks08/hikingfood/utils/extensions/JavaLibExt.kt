package com.gmail.maystruks08.hikingfood.utils.extensions

import java.util.*

fun <T> List<T>.toArrayList(): ArrayList<T> = ArrayList(this)

fun String.isSubstring(str: String): Boolean =
        (this.toLowerCase().contains(str.toLowerCase()) ||
                str.toLowerCase().contains(this.toLowerCase()))

fun <T> ArrayList<T>.clearAndAddAll(list: List<T>) {
    this.clear()
    this.addAll(list)
}

fun <T> MutableList<T>.clearAndAddAll(list: List<T>) {
    this.clear()
    this.addAll(list)
}

fun String.cut(length: Int, ellipsis: Boolean) =
        if (this.length > length) {
            if (ellipsis) {
                this.substring(0, length) + "..."
            } else {
                this.substring(0, length)
            }
        } else {
            this
        }
