import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class DetectExtensionsModuleTask : DefaultTask() {

    @Input
    lateinit var moduleName: String

    @Input
    lateinit var featureFlag: String

    @TaskAction
    fun checkModule() {
        val networkModule = project.findProject(":network")

        if (networkModule == null) {
            throw IllegalStateException("Network module ':network' not found.")
        }

        val moduleExists = networkModule.subprojects.any { it.name == moduleName }

        if (moduleExists) {
            println("Module '$moduleName' exists under network module. Enabling feature flag.")

            val commonModule = networkModule.findProject(":network:common")
                ?: throw IllegalStateException("Common module ':network:common' not found.")

            // Access the buildTypes section of the common module
            commonModule.android {
                buildTypes {
                    defaultConfig {

                        buildConfigField("Boolean", featureFlag, "true")
                    }
                }
            }
        } else {
            println("WARNING: Module '$moduleName' does not exist under network module. Feature flag not enabled.")
        }
    }
}

tasks.register<DetectExtensionsModuleTask>("detectExtensionsModule") {
    moduleName = "extensions"
    featureFlag = "EXTENSIONS_ENABLED"
}

project.tasks.getByName(":network:common:preBuild") {
    dependsOn(tasks.named("detectExtensionsModule"))
}