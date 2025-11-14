package com.muslim.qotouf.ui.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muslim.qotouf.ui.screens.home.view.HomeScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.HomeRoute
    ) {

        composable<ScreenRoute.HomeRoute> {
            HomeScreen(
                modifier = modifier
            )
        }

    }

}