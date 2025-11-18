package com.muslim.qotouf.utils.quran_parser

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muslim.qotouf.data.model.DoaaDto
import com.muslim.qotouf.utils.constant.AppConstant
import com.muslim.qotouf.utils.json_parser.loadJSONFromAssets

fun loadDoaa(context: Context): DoaaDto {
    val jsonString = context.loadJSONFromAssets(AppConstant.DOAA_FILE_NAME)
        ?: return emptyList()

    return try {
        val type = object : TypeToken<DoaaDto>() {}.type
        Gson().fromJson(jsonString, type)
    } catch (e: Exception) {
        emptyList()
    }
}
