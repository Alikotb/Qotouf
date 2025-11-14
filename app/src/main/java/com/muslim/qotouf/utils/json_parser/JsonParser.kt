package com.muslim.qotouf.utils.json_parser

import android.content.Context
import kotlin.io.bufferedReader
import kotlin.io.readText
import kotlin.io.use

fun Context.loadJSONFromAssets(fileName: String): String? {
    return try {
        this.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (e: Exception) {
        e.message
    }
}