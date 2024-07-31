package com.edwin.extension_manager.worker

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UninstallApkWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val PKG_NAME = "pkg_name"

        fun buildUninstallApkWorkRequest(pkgName: String) =
            OneTimeWorkRequestBuilder<DelegatingWorker>()
                .setInputData(UninstallApkWorker::class.delegatedData(PKG_NAME to pkgName))
                .build()
    }

    override suspend fun doWork(): Result {
        val pkgName = inputData.getString(PKG_NAME) ?: return Result.failure()
        if (context.isPackageInstalled(pkgName)) {
            val intent = Intent(Intent.ACTION_UNINSTALL_PACKAGE, "package:$pkgName".toUri())
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)

        }

        return Result.success()
    }

    /**
     * Returns true if [packageName] is installed.
     */
    private fun Context.isPackageInstalled(packageName: String): Boolean {
        return try {
            packageManager.getApplicationInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
