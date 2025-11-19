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
    //dark&Light mode
    private val _isDark = MutableStateFlow(false)
    val isDark = _isDark.asStateFlow()
//permission
    private val _permissionFlag = MutableStateFlow(false)
    val permissionFlag = _permissionFlag.asStateFlow()
    //quran notification
    private val _quranFlag = MutableStateFlow(false)
    val quranFlag = _quranFlag.asStateFlow()

    //doaa notification
    private val _doaaFlag = MutableStateFlow(false)
    val doaaFlag = _doaaFlag.asStateFlow()

    //hadith notification
    private val _hadithFlag = MutableStateFlow(false)
    val hadithFlag = _hadithFlag.asStateFlow()

    init {
        getDarkModeFalag()
        getPermissionFlag()
    }

    //dark&Light mode
    fun getDarkModeFalag() {
        viewModelScope.launch {
            repo.getData(AppConstant.IS_DARK_MODE, false).collect {
                _isDark.value = it
            }
        }
    }

    fun savDarkViewModelFlag(isDark: Boolean) {
        viewModelScope.launch {
            repo.saveData(AppConstant.IS_DARK_MODE, isDark)
        }
    }

    //permission
    fun getPermissionFlag() {
        viewModelScope.launch {
            repo.getData(AppConstant.IS_FIRST_TIME_PERMISSION, true).collect {
                _permissionFlag.value = it
            }
        }
    }

    fun savPermissionFlag(pemission: Boolean) {
        viewModelScope.launch {
            repo.saveData(AppConstant.IS_FIRST_TIME_PERMISSION, pemission)
        }
    }

    //quran notification
    fun getQuranSettingFlag() {
        viewModelScope.launch {
            repo.getData(AppConstant.QURAN_NOTIFICATION_FLAG, false).collect {
                _quranFlag.value = it
            }
        }
    }

    fun savQuranSettingFlag(pemission: Boolean) {
        viewModelScope.launch {
            repo.saveData(AppConstant.QURAN_NOTIFICATION_FLAG, pemission)
        }
    }

    //hadith notification
    fun getHadithSettingFlag() {
        viewModelScope.launch {
            repo.getData(AppConstant.HADITH_NOTIFICATION_FLAG, false).collect {
                _hadithFlag.value = it
            }
        }
    }

    fun savHadithSettingFlag(pemission: Boolean) {
        viewModelScope.launch {
            repo.saveData(AppConstant.HADITH_NOTIFICATION_FLAG, pemission)
        }
    }

    //doaa notification
    fun getDoaaSettingFlag() {
        viewModelScope.launch {
            repo.getData(AppConstant.DOAA_NOTIFICATION_FLAG, false).collect {
                _doaaFlag.value = it
            }
        }
    }

    fun savDoaaSettingFlag(pemission: Boolean) {
        viewModelScope.launch {
            repo.saveData(AppConstant.DOAA_NOTIFICATION_FLAG, pemission)
        }
    }
}