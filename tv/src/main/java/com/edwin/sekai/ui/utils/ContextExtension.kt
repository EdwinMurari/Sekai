package com.edwin.sekai.ui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Context.launchRequestPackageInstallsPermission() {
    Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).apply {
        data = Uri.parse("package:$packageName")
        startActivity(this)
    }
}