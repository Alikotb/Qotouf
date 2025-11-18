package com.muslim.qotouf.ui.screens.search.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muslim.qotouf.MyApp
import com.muslim.qotouf.data.model.Verse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
) : ViewModel() {

    private val _versesList = MutableStateFlow<List<Verse>>(emptyList())
    val versesList = _versesList.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private var allVerses: List<Verse> = emptyList()
    private var cleanVerses: List<Pair<String, Verse>> = emptyList()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true

            allVerses = MyApp.allVerses
            cleanVerses = allVerses.map { verse ->
                normalizeArabic(verse.text) to verse
            }

            _versesList.value = allVerses
            _isLoading.value = false
        }
    }

    fun searchInQotouf(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (query.isEmpty()) {
                _versesList.value = allVerses
                return@launch
            }

            val cleanQuery = normalizeArabic(query)

            val results = cleanVerses.filter { (cleanText, _) ->
                cleanText.contains(cleanQuery)
            }.map { it.second }

            _versesList.value = results
        }
    }
    private fun normalizeArabic(text: String): String {
        return text
            .replace(Regex("[\\u0610-\\u061A" +
                    "\\u064B-\\u065F" +
                    "\\u0670" +
                    "\\u06D6-\\u06ED" +
                    "\\u06EE-\\u06EF" +
                    "\\u06FA-\\u06FC" +
                    "\\u0760-\\u077F" +
                    "\\u08E3-\\u08FF]"), "")
            .replace("ـ", "")
            .replace("\u200C", "")
            .replace("\u200D", "")
            .replace("أ", "ا")
            .replace("إ", "ا")
            .replace("آ", "ا")
            .replace("ٱ", "ا")
            .replace("ى", "ي")
            .replace("ة", "ه")
            .replace("ءا", "ا")
            .replace(" ", "")
            .trim()
    }


}
