package com.muslim.qotouf.worker

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import com.muslim.qotouf.utils.constant.AppConstant
import java.util.Calendar
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            scheduleAllNotifications(context)
        }
    }
}

class MyAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val channelId = intent?.getStringExtra("channelId") ?: return
        val notificationId = intent.getIntExtra("notificationId", 0)
        val title = intent.getStringExtra("title") ?: ""
        val message = intent.getStringExtra("message") ?: ""
        val hour = intent.getIntExtra("hour", Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
        val minute = intent.getIntExtra("minute", Calendar.getInstance().get(Calendar.MINUTE))
        val requestCode = intent.getIntExtra("requestCode", notificationId)

        createNotificationChannel(
            context,
            channelId = channelId,
            name = channelId,
            descriptionText = message
        )

        showNotification(
            context,
            title = title,
            message = message,
            notificationId = notificationId,
            channelId = channelId
        )

        scheduleDailyNotification(
            context,
            hour,
            minute,
            requestCode,
            notificationId,
            channelId,
            message,
            title
        )
    }
}




fun scheduleAllNotifications(context: Context) {
    scheduleDailyNotification(context, 21, 56, 101, 1000, AppConstant.QURAN_CHANEL_ID, "الاية اليومية ","")
    scheduleDailyNotification(context, 21, 57, 102, 1001, AppConstant.HADITH_CHANEL_ID, "حديث اليوم","")
    scheduleDailyNotification(context, 21, 58, 103, 1002, AppConstant.DOAA_CHANEL_ID, "قنوت اليوم","")
}

@SuppressLint("ScheduleExactAlarm")
fun scheduleDailyNotification(
    context: Context,
    hour: Int,
    minute: Int,
    requestCode: Int,
    notificationId: Int,
    channelId: String,
    message: String,
    title: String
) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        return
    }

    val intent = Intent(context, MyAlarmReceiver::class.java).apply {
        putExtra("notificationId", notificationId)
        putExtra("channelId", channelId)
        putExtra("title", title)
        putExtra("message", message)
        putExtra("hour", hour)
        putExtra("minute", minute)
        putExtra("requestCode", requestCode)
    }



    val pendingIntent = PendingIntent.getBroadcast(
        context,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
        if (before(Calendar.getInstance())) add(Calendar.DAY_OF_YEAR, 1)
    }

    alarmManager.setExactAndAllowWhileIdle(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        pendingIntent
    )
}
