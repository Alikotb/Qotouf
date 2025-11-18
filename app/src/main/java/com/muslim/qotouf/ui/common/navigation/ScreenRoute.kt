package com.muslim.qotouf.ui.common.navigation

import kotlinx.serialization.Serializable

sealed class ScreenRoute {
    @Serializable
    object HomeRoute : ScreenRoute()

    @Serializable
    data class CurentDayTafsierRoute(val surah: String,val ayahList: String) : ScreenRoute()
    @Serializable
    object SearchRoute : ScreenRoute()

    @Serializable
    data class ThumeraRoute(val ayah: String, val chapter: Int, val verse: Int) : ScreenRoute()
}