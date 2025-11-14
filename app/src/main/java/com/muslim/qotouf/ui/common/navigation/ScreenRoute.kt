package com.muslim.qotouf.ui.common.navigation

import kotlinx.serialization.Serializable

sealed class ScreenRoute {
    @Serializable
    object HomeRoute : ScreenRoute()
}