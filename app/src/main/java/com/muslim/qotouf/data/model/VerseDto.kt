package com.muslim.qotouf.data.model


typealias VerseDTO = Map<String, List<Verse>>
data class Verse(
    val chapter: Int,
    val text: String,
    val verse: Int
)
