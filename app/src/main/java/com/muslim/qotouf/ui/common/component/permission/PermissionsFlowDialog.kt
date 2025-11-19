package com.muslim.qotouf.ui.common.component.permission

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.muslim.qotouf.MainActivity
import com.muslim.qotouf.enum.AppPermission
import com.muslim.qotouf.ui.common.component.QotoufAlertDialog


@Composable
fun PermissionsFlowDialog(
    onPermissionsFlowFinished: () -> Unit,
    context: MainActivity
) {
    val permissionsQueue = remember {
        mutableStateListOf<AppPermission>().apply {
            AppPermission.entries.forEach { permission ->
                if (!permission.isGranted(context)) {
                    add(permission)
                }
            }
        }
    }

    var showDialog by remember { mutableStateOf(true) }

    val notificationLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { _ ->
        permissionsQueue.removeFirstOrNull()
        showDialog = true
    }

    val current = permissionsQueue.firstOrNull()
    val (message, title) = when (current) {
        AppPermission.Notification -> AppPermission.Notification.message to AppPermission.Notification.title
        else -> null to null
    }

    fun skipPermission() {
        permissionsQueue.removeFirstOrNull()
        showDialog = true
    }

    if (message != null && showDialog) {
        QotoufAlertDialog(
            title = title ?: "",
            message = message,
            onConfirmLabel = "سماح",
            onConfirm = {
                showDialog = false
                when (current) {
                    AppPermission.Notification -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        } else skipPermission()
                    }

                    else -> {}
                }
            },
            onDismiss = {
                skipPermission()
            }
        )
    } else if (permissionsQueue.isEmpty()) {
        onPermissionsFlowFinished()
    }
}


