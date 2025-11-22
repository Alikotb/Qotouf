package com.muslim.qotouf.utils.quran_parser

import com.muslim.qotouf.MyApp
import com.muslim.qotouf.enum.QuranSurah


private val allVerses = MyApp.allVerses
fun getRandomQuraanAyat(): Pair<String, String> {
    val ayah = allVerses.random()
    var index = findIndexOfAyah(ayah.text)
    var combinedText = allVerses[index].text

    while (combinedText.length < 150) {
        val nextText = getNextAyah(index, allVerses[index].chapter)
        index = (index + 1) % allVerses.size
        combinedText += " * $nextText"
    }

    return Pair(combinedText, QuranSurah.getSurahName(ayah.chapter))
}

private fun getNextAyah(currentIndex: Int, surahNumber: Int): String {
    val nextIndex = currentIndex + 1
    if (nextIndex >= allVerses.size) {
        return allVerses.first().text
    }
    val nextAyah = allVerses[nextIndex]
    val isNewSurah = nextAyah.chapter != surahNumber
    return if (isNewSurah) {
        "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n${nextAyah.text}"
    } else {
        nextAyah.text
    }
}
private fun findIndexOfAyah(ayahText: String): Int {
    return allVerses.indexOfFirst { it.text == ayahText }
}