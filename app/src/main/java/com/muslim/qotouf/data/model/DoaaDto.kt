package com.muslim.qotouf.data.model

import kotlinx.serialization.Serializable

typealias DoaaDto = ArrayList<DoaaDtoItem>
@Serializable

data class DoaaDtoItem(
    val category: String,
    val duaa: List<String>,
    val id: Int
)