package com.edwin.extension_manager

interface ExtensionInstallManager {

    fun downloadAndInstallExtension(apkUrl: String, name: String)

    fun updateExtension(apkUrl: String, name: String, pkgName: String)

    fun uninstallExtension(pkgName: String)
}