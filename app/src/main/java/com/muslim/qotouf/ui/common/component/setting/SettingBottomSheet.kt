package com.muslim.qotouf.ui.common.component.setting

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muslim.qotouf.MainViewModel
import com.muslim.qotouf.enum.AppPermission
import com.muslim.qotouf.ui.common.component.permission.rememberPermissionRequestHandler

@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingBottomSheet(
    bottomSheetState: MutableState<Boolean>,
    viewModel: MainViewModel,

    ) {
    val sheetState = rememberModalBottomSheetState()
    val colors = MaterialTheme.colorScheme
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp
    val quranState = viewModel.quranFlag.collectAsStateWithLifecycle()
    val hadithState = viewModel.hadithFlag.collectAsStateWithLifecycle()
    val doaaState = viewModel.doaaFlag.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(Unit) {
        lifecycleOwner.lifecycle.addObserver(
            object : DefaultLifecycleObserver {
                override fun onResume(owner: LifecycleOwner) {
                    super.onResume(owner)

                    if (!quranState.value &&
                        AppPermission.Notification.isGranted(context)
                    ) {
                        viewModel.savQuranSettingFlag(true)
                    }

                    if (!hadithState.value &&
                        AppPermission.Notification.isGranted(context)
                    ) {
                        viewModel.savHadithSettingFlag(true)
                    }

                    if (!doaaState.value &&
                        AppPermission.Notification.isGranted(context)
                    ) {
                        viewModel.savDoaaSettingFlag(true)
                    }
                }
            }
        )
    }

    val qurannotificationPermissionHandler = rememberPermissionRequestHandler(
        permission = Manifest.permission.POST_NOTIFICATIONS,
        title = AppPermission.Notification.title,
        message = AppPermission.Notification.message,
        rationaleTitle = "إذن الإشعارات مطلوب",
        rationaleMessage = "تم رفض إذن الإشعارات مسبقًا. الرجاء تفعيله يدويًا من إعدادات التطبيق.",
        onGranted = {

            viewModel.savQuranSettingFlag(true)
        },
        onDeclined = {
            viewModel.savQuranSettingFlag(false)
        },
        permissionToBeChecked = AppPermission.Notification
    )


    val hadithNotificationPermissionHandler = rememberPermissionRequestHandler(
        permission = Manifest.permission.POST_NOTIFICATIONS,
        title = AppPermission.Notification.title,
        message = AppPermission.Notification.message,
        rationaleTitle = "إذن الإشعارات مطلوب",
        rationaleMessage = "تم رفض إذن الإشعارات مسبقًا. الرجاء تفعيله يدويًا من إعدادات التطبيق.",
        onGranted = {

            viewModel.savHadithSettingFlag(true)
        },
        onDeclined = {
            viewModel.savHadithSettingFlag(false)
        },
        permissionToBeChecked = AppPermission.Notification
    )


    val doaaNotificationPermissionHandler =
        rememberPermissionRequestHandler(
            permission = Manifest.permission.POST_NOTIFICATIONS,
            title = AppPermission.Notification.title,
            message = AppPermission.Notification.message,
            rationaleTitle = "إذن الإشعارات مطلوب",
            rationaleMessage = "تم رفض إذن الإشعارات مسبقًا. الرجاء تفعيله يدويًا من إعدادات التطبيق.",
            onGranted = {

                viewModel.savDoaaSettingFlag(true)
            },
            onDeclined = {
                viewModel.savDoaaSettingFlag(false)
            },
            permissionToBeChecked = AppPermission.Notification
        )


    LaunchedEffect(quranState, hadithState, doaaState) {
        viewModel.getDoaaSettingFlag()
        viewModel.getHadithSettingFlag()
        viewModel.getQuranSettingFlag()
    }
    ModalBottomSheet(
        onDismissRequest = { bottomSheetState.value = false },
        sheetState = sheetState,
        containerColor = Color(0xFFF5F5DB),
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = (screenHeight * .35).dp, max = (screenHeight * .65).dp)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .background(Color(0xFFF5F5DB)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "الإعدادت",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = colors.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )
            SettingSubCard(
                onClick = {
                    if (!quranState.value) {
                        qurannotificationPermissionHandler()
                    } else {
                        viewModel.savQuranSettingFlag(false)
                    }
                    //viewModel.savQuranSettingFlag(!quranState.value)
                },
                isEnabled = quranState.value
            )
            SettingSubCard(
                title = "إشعارات الحديث",
                onClick = {
                    if (!hadithState.value) {
                        hadithNotificationPermissionHandler()
                    } else {
                        viewModel.savHadithSettingFlag(false)
                    }
                },
                isEnabled = hadithState.value
            )
            SettingSubCard(
                title = "إشعارات الدعاء",
                onClick = {
                    if (!doaaState.value) {
                        doaaNotificationPermissionHandler()
                    } else {
                        viewModel.savDoaaSettingFlag(false)
                    }
                },
                isEnabled = doaaState.value
            )
        }
    }

}




