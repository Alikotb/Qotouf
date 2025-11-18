package com.muslim.qotouf.ui.screens.home.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muslim.qotouf.MyApp
import com.muslim.qotouf.data.model.Verse
import com.muslim.qotouf.enum.QuranSurah
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
) : ViewModel() {
    private val _curentDayAyah = MutableStateFlow<List<Verse>>(emptyList())
    val curentDayAyah = _curentDayAyah.asStateFlow()

    private val _curentSurah = MutableStateFlow("")
    val curentSurah = _curentSurah.asStateFlow()
    private var allVerses: List<Verse> = emptyList()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    init {
        allVerses = MyApp.allVerses
        getCurentDayaQatf()
    }

    fun getCurentDayaQatf() {
        viewModelScope.launch {
            _loading.value = true
            withContext(Dispatchers.IO) {
                getCurentDayAyah()
            }

            withContext(Dispatchers.IO) {
                getCurentSurah()
            }
            _loading.value = false
        }
    }

    private suspend fun getCurentSurah() = withContext(Dispatchers.IO) {
        if (_curentDayAyah.value.isNotEmpty()) {
            _curentSurah.value = QuranSurah.getSurahName(_curentDayAyah.value.first().chapter)
        }

    }

    private suspend fun getCurentDayAyah() = withContext(Dispatchers.IO) {

        val ayah = allVerses.random()
        //val verses = MyApp.allVerses


        var index = findIndexOfAyah(ayah.text)
        val ayahList = mutableListOf(allVerses[index])
        var combinedText = allVerses[index].text

        while (combinedText.length < 200) {
            val nextText = getNextAyah(index, allVerses[index].chapter)
            index = (index + 1) % allVerses.size
            val nextAyah = allVerses[index]
            ayahList.add(nextAyah)
            combinedText += " $nextText"
        }

        _curentDayAyah.value = ayahList

    }


    private fun findIndexOfAyah(ayahText: String): Int {
        return allVerses.indexOfFirst { it.text == ayahText }
    }

    private fun getNextAyah(currentIndex: Int, surahNumber: Int): String {
        //val verses = MyApp.allVerses
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


}