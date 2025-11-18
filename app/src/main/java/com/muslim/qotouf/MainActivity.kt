package com.muslim.qotouf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.muslim.qotouf.ui.common.component.HomeAppBar
import com.muslim.qotouf.ui.common.navigation.AppNavHost
import com.muslim.qotouf.ui.common.theme.QotoufTheme
import com.muslim.qotouf.utils.extensions.configureSystemUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private lateinit var snackBarHost: SnackbarHostState


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkTheme = remember { mutableStateOf(false) }
            val firstAppBarIcon = remember { mutableStateOf(Icons.Default.Settings) }
            val secondAppBarIcon = remember { mutableStateOf(if (isDarkTheme.value) Icons.Default.DarkMode else Icons.Default.LightMode) }
            val thirdAppBarComposable = remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
            val firstLambda = remember { mutableStateOf({}) }
            val appBarTitle = remember { mutableStateOf("قـطــــوف") }

            navController = rememberNavController()
            snackBarHost = remember { SnackbarHostState() }
            configureSystemUI(isDarkTheme.value)

            CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Rtl
            ) {
                QotoufTheme(darkTheme = isDarkTheme.value, dynamicColor = false) {
                    Scaffold(
                        topBar = {
                            HomeAppBar(
                                appBarTitle = appBarTitle,
                                onFirstIconClick = firstLambda,
                                isDarkTheme = isDarkTheme,
                                firstIcon = firstAppBarIcon.value,
                                secondIcon = secondAppBarIcon.value,
                                ThirdComposableState = thirdAppBarComposable
                                )

                        },
                        snackbarHost =  {
                            SnackbarHost(
                                hostState = snackBarHost,
                                modifier = Modifier.padding(bottom = 56.dp)
                            )
                        }
                    ) { innerPadding ->
                        AppNavHost(
                            appBarTitle = appBarTitle,
                            snackBarHost = snackBarHost,
                            onFirstIconClick = firstLambda,
                            ThirdComposable = thirdAppBarComposable,
                            firstIcon = firstAppBarIcon,
                            secondIcon = secondAppBarIcon,
                            innerPadding = innerPadding,
                            navController = navController,
                            isDarkTheme = isDarkTheme
                        )
                    }
                }
            }
        }

    }
}
