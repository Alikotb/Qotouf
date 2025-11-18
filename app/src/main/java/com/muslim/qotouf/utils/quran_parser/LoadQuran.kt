package com.muslim.qotouf.utils.quran_parser

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muslim.qotouf.data.model.VerseDTO
import com.muslim.qotouf.utils.constant.AppConstant
import com.muslim.qotouf.utils.json_parser.loadJSONFromAssets

fun loadQuran(context: Context): VerseDTO {
    val jsonString = context.loadJSONFromAssets(AppConstant.QURAN_FILE_NAME)
        ?: return emptyMap()

    return try {
        val type = object : TypeToken<VerseDTO>() {}.type
        Gson().fromJson(jsonString, type)
    } catch (e: Exception) {
        emptyMap()
    }
}
