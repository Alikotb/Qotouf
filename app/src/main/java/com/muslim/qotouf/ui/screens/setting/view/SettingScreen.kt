package com.muslim.qotouf.ui.screens.setting.view

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.muslim.qotouf.MainViewModel
import com.muslim.qotouf.enum.AppPermission
import com.muslim.qotouf.enum.QuranSurah
import com.muslim.qotouf.ui.common.component.permission.rememberPermissionRequestHandler
import com.muslim.qotouf.ui.screens.doaa.view_model.DoaaViewModel
import com.muslim.qotouf.ui.screens.hadith.view_model.HadithViewModel
import com.muslim.qotouf.ui.screens.home.view_model.HomeViewModel
import com.muslim.qotouf.ui.screens.setting.view.component.setting.CustomSlider
import com.muslim.qotouf.ui.screens.setting.view.component.setting.SettingSubCard
import com.muslim.qotouf.utils.constant.AppConstant
import com.muslim.qotouf.worker.setNotification


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun SettingScreen(
    innerPadding: PaddingValues,
    viewModel: MainViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
    hadithViewModel: HadithViewModel = hiltViewModel(),
    doaaViewModel: DoaaViewModel = hiltViewModel()
) {

    //compose configuration
    val colors = MaterialTheme.colorScheme
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    //setting states
    val quranState = viewModel.quranFlag.collectAsStateWithLifecycle()
    val hadithState = viewModel.hadithFlag.collectAsStateWithLifecycle()
    val doaaState = viewModel.doaaFlag.collectAsStateWithLifecycle()

    //home state
    val curentAyahNotificationData by homeViewModel.curentDayAyah.collectAsStateWithLifecycle()

    //hadith state
    val hadithNotificationData by hadithViewModel.curentHadith.collectAsStateWithLifecycle()

    //doaa state
    val doaaNotificationData by doaaViewModel.curentDoaa.collectAsStateWithLifecycle()

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
            setNotification(
                ctx = context,
                interval = 8,
                title = QuranSurah.getSurahName(curentAyahNotificationData.first().chapter),
                message = curentAyahNotificationData.joinToString(separator = "\n"),
                notificationId = 1000,
                channelId = AppConstant.QURAN_CHANEL_ID,
                isEnable = quranState.value,
            )
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
            setNotification(
                ctx = context,
                interval = 8,
                title = "من صحيح البخاري",
                message = hadithNotificationData?.text?:"عن أبي هريرة رضي الله عنه أن رسول الله صلى الله عليه وآله وسلم قال: (( كل أمتي يدخلون الجنة إلا من أبى)) قالوا : يا رسول الله: ومن يأبى؟! قال: ((من أطاعني دخل الجنة، ومن عصاني فقد أبى))\n",
                notificationId = 1001,
                channelId = AppConstant.HADITH_CHANEL_ID,
                isEnable = hadithState.value,
            )
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
                setNotification(
                    ctx = context,
                    interval = 6,
                    title = doaaNotificationData?.category ?: "سؤال الله الصبر",
                    message = doaaNotificationData?.duaa?.joinToString(separator = "\n")
                        ?: "ربنا ولا تحملنا ما لا طاقة لنا به",
                    notificationId = 1002,
                    channelId = AppConstant.DOAA_CHANEL_ID,
                    isEnable = doaaState.value,
                )
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
            modifier = Modifier.fillMaxWidth().padding(start = 16.dp)
        )
        }
        item {
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
        item {
            CustomSlider()
        }
    }
}