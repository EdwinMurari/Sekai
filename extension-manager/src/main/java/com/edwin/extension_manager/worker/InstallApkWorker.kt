package com.edwin.extension_manager.worker

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.core.net.toFile
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.io.File

@HiltWorker
class InstallApkWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val DOWNLOADED_APK_URI = "downloaded_apk_uri"
        const val APK_MIME = "application/vnd.android.package-archive"

        fun buildInstallApkWorkRequest() =
            OneTimeWorkRequestBuilder<DelegatingWorker>()
                .setInputData(InstallApkWorker::class.delegatedData())
                .build()
    }

    override suspend fun doWork(): Result {
        val downloadedApkUri = inputData.getString(DOWNLOADED_APK_URI) ?: return Result.failure()

        return try {
            val apkUri = Uri.parse(downloadedApkUri)
            installApk(apkUri)
            Result.success()
        } catch (e: Exception) {
            Result.failure(workDataOf("error" to e.message))
        }
    }

    private fun installApk(apkUri: Uri) {
        val apkFile = File(apkUri.path!!) // Get the File object from the Uri

        // Use FileProvider to get a content:// URI
        val contentUri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider", // Use your app's authority here
            apkFile
        )

        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(contentUri, APK_MIME)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(intent)
    }
}