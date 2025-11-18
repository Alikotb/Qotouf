package com.muslim.qotouf.data.model

import kotlinx.serialization.Serializable

typealias TafsierDto = ArrayList<TafsierDtoItem>
@Serializable

data class TafsierDtoItem(
    val id: Int,
    val sura: Int,
    val tafseir: String,
    val verse: Int
)