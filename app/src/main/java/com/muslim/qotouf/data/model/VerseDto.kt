package com.muslim.qotouf.data.model

import kotlinx.serialization.Serializable


typealias VerseDTO = Map<String, List<Verse>>
@Serializable
data class Verse(
    val chapter: Int,
    val text: String,
    val verse: Int
)
