package com.muslim.qotouf.ui.screens.home.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muslim.qotouf.MyApp
import com.muslim.qotouf.data.model.TafsierDtoItem
import com.muslim.qotouf.data.model.Verse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurentDayTafsierViewModel @Inject constructor(
) : ViewModel() {
    //ayat
    private val _curentDayTafsier = MutableStateFlow<List<Pair<Verse,TafsierDtoItem>>>(emptyList())
    val curentDayTafsier = _curentDayTafsier.asStateFlow()

    //loading state
    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()


    fun getCurentDayQatfTafseer(selectedAyah: List<Verse>) {
        val allTafsier = MyApp.allTafsier
        viewModelScope.launch {
            _loading.value = true
            val mappedList = withContext(Dispatchers.IO) {
                selectedAyah.flatMap { ayah ->
                    // get tafsier(s) for this ayah
                    allTafsier.filter { it.sura == ayah.chapter && it.verse == ayah.verse }
                        .map { tafsierItem -> ayah to tafsierItem }
                }
            }
            _curentDayTafsier.value = mappedList
            _loading.value = false
        }
    }

}
/*   */