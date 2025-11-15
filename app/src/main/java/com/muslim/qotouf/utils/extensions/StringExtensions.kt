package com.muslim.qotouf.utils.extensions

fun String.convertNumbersToArabic(): String {
    return this.map { char ->
            if (char in '0'..'9') {
                ('\u0660' + (char - '0'))
            } else {
                char
            }
        }.joinToString("")

}