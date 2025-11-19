package com.muslim.qotouf.enum

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

enum class AppPermission(val message: String, val title: String) {
    Notification("نحتاج إذنك لإرسال إشعارات الأذان في أوقاتها.", "إذن الإشعارات");
    fun isGranted(context: Context): Boolean {
        return when (this) {
            Notification -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) == PackageManager.PERMISSION_GRANTED
                } else {
                    true
                }
            }

        }
    }
}