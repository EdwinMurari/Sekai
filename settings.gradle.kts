pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "Sekai"
include(":mobile")
include(":tv")
include(":anilist")
include(":data")
include(":network:anilist")
include(":network:jikan")
include(":network:kitsu")
include(":network:common")
include(":network:extensions")
include(":network:userpref")
include(":extension-manager")
