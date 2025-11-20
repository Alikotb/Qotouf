package com.muslim.qotouf.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyPeriodicWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {

        val title = inputData.getString("title") ?: "Default Title"
        val message = inputData.getString("message") ?: "Default Message"
        val notificationId = inputData.getInt("notificationId", 0)
        val channelId = inputData.getString("channelId") ?: "general_channel"
        val isEnable = inputData.getBoolean("isEnable", true)

        if (!isEnable) {
            return Result.success()
        }

        createNotificationChannel(applicationContext, channelId, channelId, message)
        showNotification(
            applicationContext,
            title,
            message,
            notificationId,
            channelId
        )
        return Result.success()
    }
}
