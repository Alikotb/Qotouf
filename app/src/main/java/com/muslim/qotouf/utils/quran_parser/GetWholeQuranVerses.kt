package com.muslim.qotouf.utils.quran_parser

import android.content.Context
import com.muslim.qotouf.data.model.Verse

fun getWholeQuranVerses(context: Context): List<Verse> {
    val quran = loadQuran(context)
    return quran
        .toSortedMap { a, b -> a.toInt().compareTo(b.toInt()) }
        .values
        .flatten()
        .sortedWith(compareBy<Verse> { it.chapter }.thenBy { it.verse })
}