package com.muslim.qotouf.ui.screens.doaa.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muslim.qotouf.MyApp
import com.muslim.qotouf.data.model.DoaaDtoItem
import com.muslim.qotouf.data.repo.IRepo
import com.muslim.qotouf.utils.constant.AppConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoaaViewModel @Inject constructor(
    val repo: IRepo
) : ViewModel() {
    //ayat
    private val _curentDoaa = MutableStateFlow<DoaaDtoItem?>(null)
    val curentDoaa = _curentDoaa.asStateFlow()
    private var currentIndex = 0

    //loading state
    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    suspend fun getDoaa() {
        currentIndex = repo.getData(AppConstant.DOAA_INDEX, 0).first()
        val allDoaa = MyApp.allDoaa
        if (currentIndex >= allDoaa.size) currentIndex = 0
        _curentDoaa.value = allDoaa[currentIndex]
        currentIndex++
        savDoaaIndex(currentIndex)
        _loading.value = false
    }

    fun getRandomDoaa(){
        _curentDoaa.value = MyApp.allDoaa.random()
    }
    private fun savDoaaIndex(index: Int){
        viewModelScope.launch {
            repo.saveData(AppConstant.DOAA_INDEX,index)
        }
    }
}
