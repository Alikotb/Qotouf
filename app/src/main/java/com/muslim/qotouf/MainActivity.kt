package com.muslim.qotouf

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.muslim.qotouf.ui.common.component.HomeAppBar
import com.muslim.qotouf.ui.common.component.permission.PermissionsFlowDialog
import com.muslim.qotouf.ui.common.navigation.AppNavHost
import com.muslim.qotouf.ui.common.theme.AppParBackgroundDark
import com.muslim.qotouf.ui.common.theme.AppParBackgroundLight
import com.muslim.qotouf.ui.common.theme.QotoufTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private lateinit var snackBarHost: SnackbarHostState
    private lateinit var viewModel: MainViewModel


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    val notifyType = intent.getStringExtra("notify_type")
    val notifyTitle = intent.getStringExtra("notify_title")
    val notifyMessage = intent.getStringExtra("notify_message")
        enableEdgeToEdge()
        setContent {
            val colors = MaterialTheme.colorScheme
            viewModel = hiltViewModel()
            viewModel.getDarkModeFalag()
            val isDark = viewModel.isDark.collectAsStateWithLifecycle(initialValue = false)
            val isFirstTime = viewModel.permissionFlag.collectAsStateWithLifecycle(initialValue = true)

            val firstAppBarIcon = remember { mutableStateOf(Icons.Default.Settings) }
            val secondAppBarIcon = if (isDark.value) Icons.Default.DarkMode else Icons.Default.LightMode
            val thirdAppBarComposable = remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
            val firstLambda = remember { mutableStateOf({}) }
            val appBarTitle = remember { mutableStateOf("قـطــــوف") }
            val systemUiController = rememberSystemUiController()

            navController = rememberNavController()
            snackBarHost = remember { SnackbarHostState() }

            if (isFirstTime.value) {
                PermissionsFlowDialog(
                    context = this,
                    onPermissionsFlowFinished = {
                        viewModel.savPermissionFlag(false)
                    }
                )
            }

            SideEffect {
                systemUiController.setStatusBarColor(
                    color =if(isDark.value) AppParBackgroundDark else AppParBackgroundLight,
                )
            }
            CompositionLocalProvider(
                LocalLayoutDirection provides LayoutDirection.Rtl
            ) {
                QotoufTheme(darkTheme = isDark.value, dynamicColor = false) {
                    Scaffold(
                        topBar = {
                            HomeAppBar(
                                appBarTitle = appBarTitle,
                                onFirstIconClick = firstLambda,
                                firstIcon = firstAppBarIcon.value,
                                secondIcon = secondAppBarIcon,
                                ThirdComposableState = thirdAppBarComposable,
                                onSecondIconClick = {
                                    viewModel.savDarkViewModelFlag(!isDark.value)
                                }
                            )

                        },
                        snackbarHost = {
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
                            mainViewModel = viewModel,
                            isDarkTheme = isDark.value,


                            notifyType = notifyType,
                            notifyTitle = notifyTitle,
                            notifyMessage = notifyMessage
                        )
                    }

                }
            }
        }

    }
}
