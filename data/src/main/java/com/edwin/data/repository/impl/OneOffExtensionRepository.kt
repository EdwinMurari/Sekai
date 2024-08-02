package com.edwin.data.repository.impl

import com.edwin.data.mapper.asExternalModel
import com.edwin.data.model.Media
import com.edwin.network.extensions.ExtensionDataSourceFactory
import com.edwin.network.extensions.aniyomi.AnimeCatalogueSource
import com.edwin.network.extensions.impl.ExtensionSourcesFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ExtensionRepository @AssistedInject constructor(
    private val extensionSourcesFactory: ExtensionSourcesFactory,
    private val extensionDataSourceFactory: ExtensionDataSourceFactory,
    @Assisted("pkgName") private val pkgName: String
) {

    suspend fun getPopularAnime(): List<Media>? {
        val animeSource = getAnimeSource()
        return animeSource?.let {
            extensionDataSourceFactory.create(it)
                .getPopularMedia(1)
                .asExternalModel()
        }
    }

    private suspend fun getAnimeSource(): AnimeCatalogueSource? {
        return extensionSourcesFactory.create(pkgName)
            .firstOrNull { it is AnimeCatalogueSource } as? AnimeCatalogueSource
    }
}

@AssistedFactory
interface ExtensionRepositoryFactory {
    fun create(@Assisted("pkgName") pkgName: String): ExtensionRepository
}