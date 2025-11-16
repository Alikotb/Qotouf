package com.muslim.qotouf.ui.common.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muslim.qotouf.data.model.Verse
import com.muslim.qotouf.ui.screens.home.view.HomeScreen
import com.muslim.qotouf.ui.screens.search.view.SearchScreen
import com.muslim.qotouf.ui.screens.thumera.view.ThumeraScreen


@Composable
fun AppNavHost(
    navController: NavHostController,
    innerPadding: PaddingValues,
    isDarkTheme: MutableState<Boolean>,
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.HomeRoute
    ) {

        composable<ScreenRoute.HomeRoute> {
            HomeScreen(
                innerPadding = innerPadding,
                isDarkTheme = isDarkTheme,
                onSearchClick = {
                    navController.navigate(ScreenRoute.SearchRoute)
                },
            )
        }

        composable<ScreenRoute.SearchRoute> {
            SearchScreen(
                innerPadding = innerPadding,
                isDarkTheme = isDarkTheme,
                onAyahClick = { ayah, chapter, verse ->
                    navController.navigate(ScreenRoute.ThumeraRoute(ayah = ayah, chapter = chapter, verse = verse))
                }
            )
        }

        composable<ScreenRoute.ThumeraRoute> {
            val ayah = Verse(
                text = it.arguments?.getString("ayah") ?:"",
                chapter = it.arguments?.getInt("chapter")?:0,
                verse = it.arguments?.getInt("verse")?:0
            )
            ThumeraScreen(
                isDarkTheme = isDarkTheme,
                ayah = ayah,
                innerPadding = innerPadding
            )
        }


    }

}