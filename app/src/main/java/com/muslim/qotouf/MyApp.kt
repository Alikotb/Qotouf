package com.muslim.qotouf

import android.app.Application
import com.muslim.qotouf.data.model.DoaaDtoItem
import com.muslim.qotouf.data.model.HadithDtosItem
import com.muslim.qotouf.data.model.TafsierDtoItem
import com.muslim.qotouf.data.model.Verse
import com.muslim.qotouf.utils.quran_parser.loadDoaa
import com.muslim.qotouf.utils.quran_parser.loadHadith
import com.muslim.qotouf.utils.quran_parser.loadQuran
import com.muslim.qotouf.utils.quran_parser.loadTafsier
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp: Application() {
    companion object {
        lateinit var allVerses: List<Verse>
            private set
        lateinit var allDoaa: List<DoaaDtoItem>
            private set
        lateinit var allTafsier: List<TafsierDtoItem>
            private set
        lateinit var allHadith: List<HadithDtosItem>
            private set


    }

    override fun onCreate() {
        super.onCreate()

        allVerses = loadQuranOnce()
        allDoaa = loadDoaaOnce()
        allTafsier = loadTafsierOnce()
        allHadith = loadHadithOnce()
    }

    private fun loadQuranOnce(): List<Verse> {
        val quran = loadQuran(this)
        return quran
            .toSortedMap { a, b -> a.toInt().compareTo(b.toInt()) }
            .values
            .flatten()
            .sortedWith(compareBy<Verse> { it.chapter }.thenBy { it.verse })
    }

    private fun loadDoaaOnce(): List<DoaaDtoItem>{
        val doaa = loadDoaa(this)
        return doaa
    }

    private fun loadTafsierOnce(): List<TafsierDtoItem>{
        val tafsier = loadTafsier(this)
        return tafsier
    }

    private fun loadHadithOnce(): List<HadithDtosItem>{
        val hadith = loadHadith(this)
        return hadith
    }

}