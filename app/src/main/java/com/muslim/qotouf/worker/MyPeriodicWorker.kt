package com.muslim.qotouf.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.muslim.qotouf.MyApp
import com.muslim.qotouf.data.local.data_store.DataStoreImpl
import com.muslim.qotouf.utils.constant.AppConstant
import com.muslim.qotouf.utils.quran_parser.getRandomQuraanAyat

class MyPeriodicWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    private val dataStore = DataStoreImpl(context)

    override fun doWork(): Result {


        val notificationId = inputData.getInt("notificationId", 0)
        val channelId = inputData.getString("channelId") ?: "GENERAL"

        val flagKey = when (channelId) {
            AppConstant.QURAN_CHANEL_ID -> AppConstant.QURAN_NOTIFICATION_FLAG
            AppConstant.HADITH_CHANEL_ID -> AppConstant.HADITH_NOTIFICATION_FLAG
            AppConstant.DOAA_CHANEL_ID -> AppConstant.DOAA_NOTIFICATION_FLAG
            else -> ""
        }


        val isEnabled = dataStore.getBooleanSync(flagKey, false)


        if (!isEnabled) {
            return Result.success()
        }

        createNotificationChannel(
            applicationContext,
            channelId,
            "$channelId Notifications",
            channelId
        )


        when (channelId) {
            AppConstant.DOAA_CHANEL_ID -> {
            val doaa = MyApp.allDoaa.random()
                showNotification(
                    applicationContext,
                    doaa.category,
                    doaa.duaa.joinToString(separator = "\n"),
                    notificationId,
                    channelId
                )
            }
            AppConstant.HADITH_CHANEL_ID -> {
                showNotification(
                    applicationContext,
                    "من صحيـح البخـاري",
                    MyApp.allHadith.random().text,
                    notificationId,
                    channelId
                )
            }
            else -> {
                val ayah =  getRandomQuraanAyat()
                showNotification(
                    applicationContext,
                    ayah.second,
                    "بسـم اللـه الرحمـن الرحيـم\n"+ayah.first,
                    notificationId,
                    channelId
                )
            }

        }


        return Result.success()
    }
}
