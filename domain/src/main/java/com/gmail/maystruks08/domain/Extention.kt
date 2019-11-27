package com.gmail.maystruks08.domain

import java.lang.StringBuilder

fun String.addLineBreak(): String = this.plus("\n")

fun String.getRowWithSpace(value: String, maxLength: Int): String {
    val s = StringBuilder()
    if (maxLength - (this.length + value.length) > MIN_SPACE &&
        maxLength > this.length + value.length
        && this.length < MAX_WORD_LENGTH) {
        s.append(this)
        val spaceNum = maxLength - (this.length + value.length)
        for (i in 0 until spaceNum - 1) {
            s.append(EMPTY_CHAR)
        }
    } else if (this.contains(EMPTY_CHAR)) {
        val indexSpace = this.indexOf(EMPTY_CHAR)
        for (i in this.indices) {
            if (i == indexSpace) {
                s.appendln()
                continue
            }
            s.append(this[i])
        }
        val spaceNum = maxLength - (this.length - indexSpace + value.length)
        for (i in 0 until spaceNum) {
            s.append(EMPTY_CHAR)
        }
    } else {
        for (i in this.indices) {
            if (i == MAX_WORD_LENGTH) {
                s.appendln()
                continue
            }
            s.append(this[i])
        }
        val spaceNum = maxLength - (this.length - MAX_WORD_LENGTH + value.length)
        for (i in 0 until spaceNum) {
            s.append(EMPTY_CHAR)
        }
    }
    s.append(value)
    return s.toString()
}

