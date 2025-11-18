package com.muslim.qotouf.data.model

import kotlinx.serialization.Serializable

typealias HadithDto = ArrayList<HadithDtosItem>
@Serializable

data class HadithDtosItem(
    val id: Int,
    val text: String
)