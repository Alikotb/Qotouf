package com.muslim.qotouf.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.muslim.qotouf.MainActivity
import com.muslim.qotouf.R
import java.util.concurrent.TimeUnit


fun setNotification(
    ctx: Context,
    title: String,
    message: String,
    notificationId: Int,
    channelId: String,
    interval: Long,
    isEnable: Boolean
) {

    if (!isEnable) {
        WorkManager.getInstance(ctx)
            .cancelUniqueWork("periodic_$channelId")
        return
    }

    val data = workDataOf(
        "title" to title,
        "message" to message,
        "notificationId" to notificationId,
        "channelId" to channelId
    )

    val request = PeriodicWorkRequestBuilder<MyPeriodicWorker>(
        interval, TimeUnit.MINUTES
    )
        .setInputData(data)
        .build()

    WorkManager.getInstance(ctx).enqueueUniquePeriodicWork(
        "periodic_$channelId",
        ExistingPeriodicWorkPolicy.UPDATE,
        request
    )
}

fun showNotification(
    context: Context,
    title: String,
    message: String,
    notificationId: Int,
    channelId: String
) {
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val intent = Intent(context, MainActivity::class.java).apply {
        putExtra("notify_type", channelId)
        putExtra("notify_title", title)
        putExtra("notify_message", message)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val contentIntent = PendingIntent.getActivity(
        context,
        notificationId,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.logo)
        .setContentTitle("قطــــوف")
        .setContentText(title)
        .setContentIntent(contentIntent)
        .setAutoCancel(true)
        .setStyle(NotificationCompat.BigTextStyle().bigText(message))
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    manager.notify(notificationId, builder.build())
}

fun createNotificationChannel(
    context: Context,
    name: String,
    descriptionText: String,
    channelId: String
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            name,
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.description = descriptionText

        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }
}
