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

fun String.normalizeArabic(): String {
    return this
        .replace(Regex("[\\u0610-\\u061A" +
                "\\u064B-\\u065F" +
                "\\u0670" +
                "\\u06D6-\\u06ED" +
                "\\u06EE-\\u06EF" +
                "\\u06FA-\\u06FC" +
                "\\u0760-\\u077F" +
                "\\u08E3-\\u08FF]"), "")
        .replace("ـ", "")
        .replace("\u200C", "")
        .replace("\u200D", "")
        .replace("أ", "ا")
        .replace("إ", "ا")
        .replace("آ", "ا")
        .replace("ٱ", "ا")
        .replace("ى", "ي")
        .replace("ة", "ه")
        .replace("ءا", "ا")
        .replace(" ", "")
        .trim()
}