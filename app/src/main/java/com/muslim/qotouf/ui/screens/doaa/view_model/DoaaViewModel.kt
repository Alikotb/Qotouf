package com.muslim.qotouf.ui.screens.doaa.view_model

import androidx.lifecycle.ViewModel
import com.muslim.qotouf.MyApp
import com.muslim.qotouf.data.model.DoaaDtoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class DoaaViewModel @Inject constructor(
) : ViewModel() {
    //ayat
    private val _curentDoaa = MutableStateFlow<DoaaDtoItem?>(null)
    val curentDoaa = _curentDoaa.asStateFlow()
    private var currentIndex = 0

    //loading state
    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    fun getDoaa() {
        val allDoaa = MyApp.allDoaa


        if (currentIndex >= allDoaa.size) {
            currentIndex = 0
        }

        _curentDoaa.value = allDoaa[currentIndex]

        currentIndex++

        _loading.value = false
    }

    fun getRandomDoaa() {
      MyApp.allHadith

        _curentDoaa.value =  MyApp.allDoaa.random()
        _loading.value = false
    }

}
