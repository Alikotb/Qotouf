package com.muslim.qotouf.data.model

import kotlinx.serialization.Serializable

typealias HadithDto = List<HadithDtosItem>
@Serializable

data class HadithDtosItem(
    val id: Int,
    val text: String
)