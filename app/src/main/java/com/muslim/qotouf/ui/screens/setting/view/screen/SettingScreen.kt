package com.muslim.qotouf.ui.screens.setting.view.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muslim.qotouf.MainViewModel
import com.muslim.qotouf.enum.AppPermission
import com.muslim.qotouf.ui.common.component.permission.rememberPermissionRequestHandler
import com.muslim.qotouf.ui.screens.setting.view.component.SettingNotificationCard
import com.muslim.qotouf.ui.screens.setting.view.component.SettingTextSizeCard
import com.muslim.qotouf.utils.constant.AppConstant
import com.muslim.qotouf.worker.setNotification


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun SettingScreen(
    innerPadding: PaddingValues,
    viewModel: MainViewModel,
) {

    //compose configuration
    val colors = MaterialTheme.colorScheme
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp
    val context = LocalContext.current
    //setting states
    val quranState = viewModel.quranFlag.collectAsStateWithLifecycle()
    val hadithState = viewModel.hadithFlag.collectAsStateWithLifecycle()
    val doaaState = viewModel.doaaFlag.collectAsStateWithLifecycle()



    //font size
    val quranTextSize by viewModel.quranTextSize.collectAsStateWithLifecycle()
    val adkarTextSize by viewModel.adkarTextSize.collectAsStateWithLifecycle()

    val (qurannotificationPermissionHandler, hadithNotificationPermissionHandler, doaaNotificationPermissionHandler) = triple(
        context,
        viewModel,
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(colors.surfaceContainer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Spacer(Modifier.height(screenHeight * 0.02f))
            Text(
                text = "إعدادت الأشعار",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = colors.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
        }
        item {
            SettingNotificationCard(
                onClick = {
                    if (!quranState.value) {
                        qurannotificationPermissionHandler()
                    } else {
                        viewModel.savQuranSettingFlag(false)
                    }
                },
                isEnabled = quranState.value
            )
            SettingNotificationCard(
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
            SettingNotificationCard(
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
        item{
            Spacer(Modifier.height(16.dp))
            Text(
                text = "تخصيــص الخطــوط",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = colors.primary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            )
        }
        item {
            SettingTextSizeCard(
                textSize = quranTextSize
            ){
                viewModel.savQuranTextSize(it)
            }
        }
        item {
            SettingTextSizeCard(
                title = "حجم الدعاء والحديث",
                example =  "رَبَّنَا ظَلَمْنَا أَنْفُسَنَا وَإِنْ لَمْ تَغْفِرْ لَنَا وَتَرْحَمْنَا لَنَكُونَنَّ مِنَ الْخَاسِرِينَ",
                textSize = adkarTextSize
            ){
                viewModel.savAdkarTextSize(it)
            }
        }
    }
}

@Composable
private fun triple(
    context: Context,
    viewModel: MainViewModel,
): Triple<() -> Unit, () -> Unit, () -> Unit> {
    val qurannotificationPermissionHandler = rememberPermissionRequestHandler(
        permission = Manifest.permission.POST_NOTIFICATIONS,
        title = AppPermission.Notification.title,
        message = AppPermission.Notification.message,
        rationaleTitle = "إذن الإشعارات مطلوب",
        rationaleMessage = "تم رفض إذن الإشعارات مسبقًا. الرجاء تفعيله يدويًا من إعدادات التطبيق.",
        onGranted = {
            viewModel.savQuranSettingFlag(true)
            setNotification(
                ctx = context,
                notificationId = 1000,
                channelId = AppConstant.QURAN_CHANEL_ID,
                interval = 8L,
                isEnable = true
            )
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
            setNotification(
                ctx = context,
                notificationId = 1001,
                channelId = AppConstant.HADITH_CHANEL_ID,
                interval = 8L,
                isEnable = true
            )
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
                setNotification(
                    ctx = context,
                    notificationId = 1002,
                    channelId = AppConstant.DOAA_CHANEL_ID,
                    interval = 6L,
                    isEnable = true
                )
            },
            onDeclined = {
                viewModel.savDoaaSettingFlag(false)
            },
            permissionToBeChecked = AppPermission.Notification
        )
    return Triple(
        qurannotificationPermissionHandler,
        hadithNotificationPermissionHandler, doaaNotificationPermissionHandler
    )
}