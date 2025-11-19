package com.muslim.qotouf.ui.screens.hadith.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muslim.qotouf.MyApp
import com.muslim.qotouf.data.model.HadithDtosItem
import com.muslim.qotouf.data.repo.RepoImpl
import com.muslim.qotouf.utils.constant.AppConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HadithViewModel @Inject constructor(
    val repo: RepoImpl
) : ViewModel() {
    //ayat
    private val _curentHadith = MutableStateFlow<HadithDtosItem?>(null)
    val curentHadith = _curentHadith.asStateFlow()
    private var currentIndex = 0

    //loading state
    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    suspend fun getHadith() {
        currentIndex = repo.getData(AppConstant.HADITH_INDEX, 0).first()
        val allHadith = MyApp.allHadith
        if (currentIndex >= allHadith.size) currentIndex = 0
        _curentHadith.value = allHadith[currentIndex]
        currentIndex++
        savDoaaIndex(currentIndex)
        _loading.value = false
    }

    fun getRandomHadith(){
        _curentHadith.value = MyApp.allHadith.random()
    }
    private fun savDoaaIndex(index: Int){
        viewModelScope.launch {
            repo.saveData(AppConstant.HADITH_INDEX,index)
        }
    }

}
