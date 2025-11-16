package com.muslim.qotouf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkTheme = remember { mutableStateOf(false) }

            navController = rememberNavController()
            configureSystemUI(isDarkTheme.value)

            CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Rtl
            ) {
                QotoufTheme(darkTheme = isDarkTheme.value, dynamicColor = false) {
                    Scaffold(
                        topBar = {
                            HomeAppBar(isDarkTheme = isDarkTheme)

                        }
                    ) { innerPadding ->
                        AppNavHost(
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
