package com.muslim.qotouf.utils.quran_parser

import android.content.Context
import com.muslim.qotouf.data.model.Verse

fun getDayAyah(context: Context): Verse{
    return getWholeQuranVerses(context).random()
}