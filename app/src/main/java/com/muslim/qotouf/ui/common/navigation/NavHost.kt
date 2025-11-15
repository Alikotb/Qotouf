package com.muslim.qotouf.ui.common.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muslim.qotouf.ui.screens.home.view.HomeScreen
import com.muslim.qotouf.ui.screens.search.view.SearchScreen


@Composable
fun AppNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.HomeRoute
    ) {

        composable<ScreenRoute.HomeRoute> {
            HomeScreen(
                innerPadding = innerPadding,
                onSearchClick = {
                    navController.navigate(ScreenRoute.SearchRoute)
                }
            )
        }

        composable<ScreenRoute.SearchRoute> {
            SearchScreen(
                innerPadding = innerPadding
            )
        }


    }

}