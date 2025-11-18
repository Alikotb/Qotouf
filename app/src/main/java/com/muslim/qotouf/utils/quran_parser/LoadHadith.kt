package com.muslim.qotouf.utils.quran_parser

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muslim.qotouf.data.model.HadithDto
import com.muslim.qotouf.utils.constant.AppConstant
import com.muslim.qotouf.utils.json_parser.loadJSONFromAssets

fun loadHadith(context: Context): HadithDto {
    val jsonString = context.loadJSONFromAssets(AppConstant.HADITH_FILE_NAME)
        ?: return emptyList()

    return try {
        val type = object : TypeToken<HadithDto>() {}.type
        Gson().fromJson(jsonString, type)
    } catch (e: Exception) {
        emptyList()
    }
}
