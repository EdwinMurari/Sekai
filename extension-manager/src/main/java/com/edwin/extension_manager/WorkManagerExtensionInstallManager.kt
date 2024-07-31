package com.edwin.extension_manager

import android.content.Context
import androidx.work.WorkManager
import com.edwin.extension_manager.worker.DownloadApkWorker
import com.edwin.extension_manager.worker.InstallApkWorker
import com.edwin.extension_manager.worker.UninstallApkWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WorkManagerExtensionInstallManager @Inject constructor(
    @ApplicationContext private val context: Context,
) : ExtensionInstallManager {

    override fun downloadAndInstallExtension(apkUrl: String, name: String) {
        val workManager = WorkManager.getInstance(context)
        workManager
            .beginWith(DownloadApkWorker.buildDownloadApkWorkRequest(apkUrl, name))
            .then(InstallApkWorker.buildInstallApkWorkRequest())
            .enqueue()
    }

    override fun updateExtension(apkUrl: String, name: String, pkgName: String) {
        val workManager = WorkManager.getInstance(context)
        workManager
            .beginWith(DownloadApkWorker.buildDownloadApkWorkRequest(apkUrl, name))
            .then(UninstallApkWorker.buildUninstallApkWorkRequest(pkgName))
            .then(InstallApkWorker.buildInstallApkWorkRequest())
            .enqueue()
    }

    override fun uninstallExtension(pkgName: String) {
        val workManager = WorkManager.getInstance(context)
        workManager
            .beginWith(UninstallApkWorker.buildUninstallApkWorkRequest(pkgName))
            .enqueue()
    }
}