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


    //fontSize
    private val _quranTextSize = MutableStateFlow(0)
    val quranTextSize = _quranTextSize.asStateFlow()

    private val _adkarTextSize = MutableStateFlow(0)
    val adkarTextSize = _adkarTextSize.asStateFlow()



    init {
        getDarkModeFalag()
        getPermissionFlag()
        collectSettings()
    }

    private fun collectSettings() {
        viewModelScope.launch {
            repo.getData(AppConstant.IS_DARK_MODE, false).collect {
                _isDark.value = it
            }
        }

        viewModelScope.launch {
            repo.getData(AppConstant.IS_FIRST_TIME_PERMISSION, true).collect {
                _permissionFlag.value = it
            }
        }

        viewModelScope.launch {
            repo.getData(AppConstant.QURAN_NOTIFICATION_FLAG, false).collect {
                _quranFlag.value = it
            }
        }

        viewModelScope.launch {
            repo.getData(AppConstant.HADITH_NOTIFICATION_FLAG, false).collect {
                _hadithFlag.value = it
            }
        }

        viewModelScope.launch {
            repo.getData(AppConstant.DOAA_NOTIFICATION_FLAG, false).collect {
                _doaaFlag.value = it
            }
        }

        //font size
        viewModelScope.launch {
            repo.getData(AppConstant.QURAN_TEXT_SIZE, 22).collect {
                _quranTextSize.value = it
            }
        }

        viewModelScope.launch {
            repo.getData(AppConstant.ADKAR_TEXT_SIZE, 22).collect {
                _adkarTextSize.value = it
            }
        }
    }

    fun savQuranSettingFlag(value: Boolean) {
        viewModelScope.launch {
            repo.saveData(AppConstant.QURAN_NOTIFICATION_FLAG, value)
        }
    }

    fun savHadithSettingFlag(value: Boolean) {
        viewModelScope.launch {
            repo.saveData(AppConstant.HADITH_NOTIFICATION_FLAG, value)
        }
    }

    fun savDoaaSettingFlag(value: Boolean) {
        viewModelScope.launch {
            repo.saveData(AppConstant.DOAA_NOTIFICATION_FLAG, value)
        }
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

    fun savQuranTextSize(value: Int) {
        viewModelScope.launch {
            repo.saveData(AppConstant.QURAN_TEXT_SIZE, value)
        }
    }

    fun savAdkarTextSize(value: Int) {
        viewModelScope.launch {
            repo.saveData(AppConstant.ADKAR_TEXT_SIZE, value)
        }
    }

}