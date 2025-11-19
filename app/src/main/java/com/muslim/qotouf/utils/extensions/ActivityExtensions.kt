package com.muslim.qotouf.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}

fun Activity.isPermissionPermanentlyDenied(
    permission: String
): Boolean {
    return !ActivityCompat.shouldShowRequestPermissionRationale(this, permission) &&
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED
}