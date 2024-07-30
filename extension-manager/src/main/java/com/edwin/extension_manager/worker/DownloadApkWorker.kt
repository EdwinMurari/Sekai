package com.edwin.extension_manager.worker

import android.content.Context
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.edwin.data.repository.ExtensionsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@HiltWorker
class DownloadApkWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParams: WorkerParameters,
    private val extensionsRepository: ExtensionsRepository
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val EXTENSION_NAME = "extension_name"
        const val DOWNLOADED_APK_URI = "downloaded_apk_uri"
        const val DOWNLOAD_URL = "download_url"
        const val PROGRESS = "progress"

        fun buildDownloadApkWorkRequest(apkUrl: String, extensionName: String) =
            OneTimeWorkRequestBuilder<DelegatingWorker>()
                .setInputData(
                    DownloadApkWorker::class.delegatedData(
                        DOWNLOAD_URL to apkUrl,
                        EXTENSION_NAME to extensionName
                    )
                )
                .build()
    }

    override suspend fun doWork(): Result {
        val apkUrl = inputData.getString(DOWNLOAD_URL) ?: return Result.failure()
        val extensionName = inputData.getString(EXTENSION_NAME) ?: return Result.failure()

        return withContext(Dispatchers.IO) {
            try {
                val responseBody = extensionsRepository.downloadExtensionApk(apkUrl)
                val totalBytes = responseBody.contentLength()
                var downloadedBytes = 0L

                val file = File(context.cacheDir, "$extensionName.apk")
                val outputStream = FileOutputStream(file)

                responseBody.byteStream().use { inputStream ->
                    outputStream.use { outputStream ->
                        val buffer = ByteArray(4 * 1024) // 4 KB buffer
                        var bytesRead: Int
                        while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                            outputStream.write(buffer, 0, bytesRead)
                            downloadedBytes += bytesRead
                            val progress = (downloadedBytes * 100 / totalBytes).toInt()

                            setProgress(workDataOf(PROGRESS to progress))
                        }
                    }
                }

                Result.success(workDataOf(DOWNLOADED_APK_URI to file.toUri().toString()))
            } catch (e: IOException) {
                Result.failure()
            }
        }
    }
}