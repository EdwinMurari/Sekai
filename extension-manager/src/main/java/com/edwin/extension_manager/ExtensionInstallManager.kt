package com.edwin.extension_manager

interface ExtensionInstallManager {

    fun downloadAndInstallExtension(apkUrl: String, name: String)
}