package com.muslim.qotouf.ui.screens.hadith.view_model

import androidx.lifecycle.ViewModel
import com.muslim.qotouf.MyApp
import com.muslim.qotouf.data.model.HadithDtosItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HadithViewModel @Inject constructor(
) : ViewModel() {
    //ayat
    private val _curentHadith = MutableStateFlow<HadithDtosItem?>(null)
    val curentHadith = _curentHadith.asStateFlow()
    private var currentIndex = 0

    //loading state
    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    fun getHadith() {
        val allHadith = MyApp.allHadith


        if (currentIndex >= allHadith.size) {
            currentIndex = 0
        }

        _curentHadith.value = allHadith[currentIndex]

        currentIndex++

        _loading.value = false
    }

    fun getRandomHadith() {
      MyApp.allHadith

        _curentHadith.value =  MyApp.allHadith.random()
        _loading.value = false
    }

}
