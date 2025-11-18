package com.muslim.qotouf.data.model

import kotlinx.serialization.Serializable

typealias TafsierDto = List<TafsierDtoItem>
@Serializable

data class TafsierDtoItem(
    val id: Int,
    val sura: Int,
    val tafseir: String,
    val verse: Int
)