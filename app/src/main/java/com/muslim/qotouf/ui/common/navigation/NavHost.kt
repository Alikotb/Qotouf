package com.muslim.qotouf.ui.common.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.muslim.qotouf.data.model.Verse
import com.muslim.qotouf.ui.screens.home.view.HomeScreen
import com.muslim.qotouf.ui.screens.search.view.SearchScreen
import com.muslim.qotouf.ui.screens.search.view.component.SearchThirdAppBarIcon
import com.muslim.qotouf.ui.screens.thumera.view.ThumeraScreen
import com.muslim.qotouf.ui.screens.thumera.view_model.ScreenshotController
import com.muslim.qotouf.ui.screens.thumera.view_model.ThumeraViewModel


@Composable
fun AppNavHost(
    ThirdComposable: MutableState<@Composable (() -> Unit)?>,
    navController: NavHostController,
    innerPadding: PaddingValues,
    isDarkTheme: MutableState<Boolean>,
    secondIcon: MutableState<ImageVector>,
    firstIcon: MutableState<ImageVector>,
    onFirstIconClick: MutableState<() -> Unit>,
    snackBarHost: SnackbarHostState,
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoute.HomeRoute
    ) {

        composable<ScreenRoute.HomeRoute> {
            firstIcon.value = Icons.Default.Settings
            ThirdComposable.value = null
            onFirstIconClick.value ={}
            HomeScreen(
                snackBarHost = snackBarHost,
                innerPadding = innerPadding,
                onSearchClick = {
                    navController.navigate(ScreenRoute.SearchRoute)
                },
                isDarkTheme = isDarkTheme
            )
        }

        composable<ScreenRoute.SearchRoute> {
            firstIcon.value = Icons.AutoMirrored.Filled.ArrowForward
            ThirdComposable.value = null
            onFirstIconClick.value ={
                navController.popBackStack()
            }

            SearchScreen(
                snackBarHost = snackBarHost,
                innerPadding = innerPadding,
                isDarkTheme = isDarkTheme,
                onAyahClick = { ayah, chapter, verse ->
                    navController.navigate(
                        ScreenRoute.ThumeraRoute(
                            ayah = ayah,
                            chapter = chapter,
                            verse = verse
                        )
                    )
                }
            )
        }

        composable<ScreenRoute.ThumeraRoute> {
            val viewModel: ThumeraViewModel = hiltViewModel()
            firstIcon.value = Icons.Default.Share
            val screenshotController = remember { ScreenshotController() }

            val ayah = Verse(
                text = it.arguments?.getString("ayah") ?: "",
                chapter = it.arguments?.getInt("chapter") ?: 0,
                verse = it.arguments?.getInt("verse") ?: 0
            )

            onFirstIconClick.value = {
                screenshotController.capture { bitmap ->
                    viewModel.shareBitmapDirectly(bitmap)
                }
            }

            ThirdComposable.value = {
                SearchThirdAppBarIcon {
                    screenshotController.capture { bitmap ->
                        viewModel.saveBitmapToGallery(bitmap, ayah.chapter, ayah.verse)
                    }
                }
            }
            ThumeraScreen(
                snackBarHost = snackBarHost,
                ayah = ayah,
                innerPadding = innerPadding,
                isDarkTheme = isDarkTheme,
                screenshotController = screenshotController
            )
        }

    }
}

