package com.muslim.qotouf.ui.common.navigation

import android.net.Uri
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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.muslim.qotouf.data.model.Verse
import com.muslim.qotouf.enum.QuranSurah
import com.muslim.qotouf.ui.screens.doaa.view.DoaaScreen
import com.muslim.qotouf.ui.screens.hadith.view.HadithScreen
import com.muslim.qotouf.ui.screens.home.view.scrrens.CurentDayTafsirScreen
import com.muslim.qotouf.ui.screens.home.view.scrrens.HomeScreen
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
    isDarkTheme: Boolean,
    secondIcon: ImageVector,
    firstIcon: MutableState<ImageVector>,
    onFirstIconClick: MutableState<() -> Unit>,
    snackBarHost: SnackbarHostState,
    appBarTitle: MutableState<String>,
    seetingBottomSheetState: MutableState<Boolean>,
) {
    val screenshotController = remember { ScreenshotController() }
    val viewModel: ThumeraViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = ScreenRoute.HomeRoute
    ) {

        composable<ScreenRoute.HomeRoute> {
            appBarTitle.value = "قـطــــوف"
            firstIcon.value = Icons.Default.Settings
            ThirdComposable.value = null
            onFirstIconClick.value = {
                seetingBottomSheetState.value = true
            }

            HomeScreen(
                innerPadding = innerPadding,
                onSearchClick = {
                    navController.navigate(ScreenRoute.SearchRoute)
                },
                isDarkTheme = isDarkTheme,
                onDayTafsierClick = { surah, json ->
                    navController.navigate(ScreenRoute.CurentDayTafsierRoute(surah, json))
                },
                onDoaaClick = {
                    navController.navigate(ScreenRoute.DoaaRoute)
                },
                onHadithClick = {
                    navController.navigate(ScreenRoute.HadithRoute)
                }
            )
        }
        composable<ScreenRoute.CurentDayTafsierRoute> {
            val surah = it.arguments?.getString("surah") ?: ""

            val json = it.arguments?.getString("ayahList") ?: "[]"

            val decodedJson = Uri.decode(json)
            val type = object : TypeToken<List<Verse>>() {}.type
            val ayahList: List<Verse> = Gson().fromJson(decodedJson, type)
            appBarTitle.value = "المختصر في التفسير"

            firstIcon.value = Icons.AutoMirrored.Filled.ArrowForward
            ThirdComposable.value = null
            onFirstIconClick.value = {
                navController.popBackStack()
            }
            CurentDayTafsirScreen(
                surah = surah,
                innerPadding = innerPadding,
                ayahList = ayahList,
            )
        }

        composable<ScreenRoute.SearchRoute> {
            appBarTitle.value = "أقطف ثمرة"

            firstIcon.value = Icons.AutoMirrored.Filled.ArrowForward
            ThirdComposable.value = null
            onFirstIconClick.value = {
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
            appBarTitle.value = "قـطــــوف"
            firstIcon.value = Icons.Default.Share
            val ayah = Verse(
                text = it.arguments?.getString("ayah") ?: "",
                chapter = it.arguments?.getInt("chapter") ?: 0,
                verse = it.arguments?.getInt("verse") ?: 0
            )

            val screenTitle = QuranSurah.getSurahName(ayah.chapter)

            ScreenShootAction(onFirstIconClick, screenshotController, viewModel, ayah.verse,screenTitle,ThirdComposable)
            ThumeraScreen(
                snackBarHost = snackBarHost,
                ayah = ayah,
                innerPadding = innerPadding,
                isDarkTheme = isDarkTheme,
                screenshotController = screenshotController,
                onDayTafsierClick = { surah, json ->
                    navController.navigate(ScreenRoute.CurentDayTafsierRoute(surah, json))
                }
            )
        }

        composable<ScreenRoute.HadithRoute> {
            appBarTitle.value = "صحيح البخاري"
            firstIcon.value = Icons.Default.Share
            val id = ((System.currentTimeMillis() / 1000) % 1000000).toInt()

            ScreenShootAction(onFirstIconClick, screenshotController, viewModel,id , "صحيح البخاري",ThirdComposable)

            HadithScreen(
                screenshotController = screenshotController,
                innerPadding = innerPadding,
            )
        }
        composable<ScreenRoute.DoaaRoute> {
            appBarTitle.value = "الدعاء"
            firstIcon.value = Icons.Default.Share
            val id = ((System.currentTimeMillis() / 1000) % 1000000).toInt()

            ScreenShootAction(onFirstIconClick, screenshotController, viewModel, id,"الدعاء",ThirdComposable)

            DoaaScreen(
                screenshotController = screenshotController,
                innerPadding = innerPadding,
            )
        }

    }
}

@Composable
private fun ScreenShootAction(
    onFirstIconClick: MutableState<() -> Unit>,
    screenshotController: ScreenshotController,
    viewModel: ThumeraViewModel,
    screenId: Int,
    screenTitle: String,
    ThirdComposable: MutableState<@Composable (() -> Unit)?>
) {
    onFirstIconClick.value = {
        screenshotController.capture { bitmap ->
            viewModel.shareBitmapDirectly(bitmap)
        }
    }

    ThirdComposable.value = {
        SearchThirdAppBarIcon {
            screenshotController.capture { bitmap ->
                viewModel.saveBitmapToGallery(bitmap, screenTitle, screenId)
            }
        }
    }
}

