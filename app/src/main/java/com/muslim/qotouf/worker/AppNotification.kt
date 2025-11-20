package com.muslim.qotouf.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.muslim.qotouf.R
import java.util.concurrent.TimeUnit

fun showNotification(
    context: Context,
    title: String,
    message: String,
    notificationId: Int,
    channelId: String
) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.logo)

    val notificationBuilder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.logo)
        .setContentTitle("قطــــوف")
        .setContentText(title)
        .setStyle(NotificationCompat.BigTextStyle().bigText(message))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_RECOMMENDATION)
        .setLargeIcon(largeIcon)

    notificationManager.notify(notificationId, notificationBuilder.build())
}


fun createNotificationChannel(
    context: Context,
    name: String,
    descriptionText: String,
    channelId: String
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun setNotification(
    ctx: Context,
    interval:Long,
    title: String,
    message: String,
    notificationId: Int,
    channelId: String,
    isEnable: Boolean
){

    val data = workDataOf(
        "title" to title,
        "message" to message,
        "notificationId" to notificationId,
        "channelId" to channelId,
        "isEnable" to isEnable
    )

    val workRequest = PeriodicWorkRequestBuilder<MyPeriodicWorker>(
        interval , TimeUnit.HOURS
    )
        .setInputData(data)
        .build()

    WorkManager.getInstance(ctx).enqueueUniquePeriodicWork(
        "my_periodic_notification",
        ExistingPeriodicWorkPolicy.REPLACE,
        workRequest
    )

}
