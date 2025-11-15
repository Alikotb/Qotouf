package com.muslim.qotouf.ui.screens.home.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import com.muslim.qotouf.data.model.Verse
import com.muslim.qotouf.enum.QuranSurah
import com.muslim.qotouf.utils.quran_parser.getDayAyah
import com.muslim.qotouf.utils.quran_parser.getWholeQuranVerses
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {
    private val _curentDayAyah = MutableStateFlow<List<Verse>>(emptyList())
    val curentDayAyah = _curentDayAyah.asStateFlow()

    private val _curentSurah = MutableStateFlow<String>("")
    val curentSurah = _curentSurah.asStateFlow()

    init {
        getCurentDayAyah()
        getCurentSurah()
    }

    fun getCurentSurah() {
        _curentSurah.value = QuranSurah.getSurahName(_curentDayAyah.value.first().chapter)

    }

    fun getCurentDayAyah() {
        val ayah = getDayAyah(context)
        val verses = getWholeQuranVerses(context)

        var index = findIndexOfAyah(ayah.text)
        val ayahList = mutableListOf(verses[index])
        var combinedText = verses[index].text

        while (combinedText.length < 50) {
            val nextText = getNextAyah(index, verses[index].chapter)
            index = (index + 1) % verses.size
            val nextAyah = verses[index]
            ayahList.add(nextAyah)
            combinedText += " $nextText"
        }

        _curentDayAyah.value = ayahList
    }


    private fun  findIndexOfAyah(ayahText: String): Int {
        return getWholeQuranVerses(context).indexOfFirst { it.text == ayahText }
    }

    private fun getNextAyah(currentIndex: Int, surahNumber: Int): String {
        val verses = getWholeQuranVerses(context)
        val nextIndex = currentIndex + 1
        if (nextIndex >= verses.size) {
            return verses.first().text
        }
        val nextAyah = verses[nextIndex]
        val isNewSurah = nextAyah.chapter != surahNumber
        return if (isNewSurah && nextAyah.chapter != 9) {
            "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\n${nextAyah.text}"
        } else {
            nextAyah.text
        }
    }



}