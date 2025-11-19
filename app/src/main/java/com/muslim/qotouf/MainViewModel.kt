package com.muslim.qotouf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muslim.qotouf.data.repo.IRepo
import com.muslim.qotouf.utils.constant.AppConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val repo: IRepo
) : ViewModel() {
    private val _isDark = MutableStateFlow<Boolean>(false)
    val isDark = _isDark.asStateFlow()

    init {
        getDarkModeFalag()
    }

    fun getDarkModeFalag(){
        viewModelScope.launch {
            repo.getData(AppConstant.IS_DARK_MODE,false).collect{
                _isDark.value = it
            }
        }
    }
    fun savDarkViewModelFlag(isDark: Boolean){
        viewModelScope.launch {
            repo.saveData(AppConstant.IS_DARK_MODE, isDark)
        }
    }

}