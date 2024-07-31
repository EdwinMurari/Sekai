package com.edwin.data.repository

import com.edwin.data.model.Extension
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody

interface ExtensionsRepository {

    fun getExtensions(): Flow<List<Extension>>

    suspend fun downloadExtensionApk(apkUrl: String): ResponseBody
}