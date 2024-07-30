package com.edwin.data.repository

import com.edwin.data.model.Extension
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface ExtensionsRepository {

    fun getAvailableExtensions(): Flow<List<Extension.Available>>

    suspend fun downloadExtensionApk(apkUrl: String): ResponseBody
}