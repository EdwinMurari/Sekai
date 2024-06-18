package com.edwin.sekai.domain

import com.edwin.data.model.MediaDetails
import com.edwin.data.model.NetworkResponse
import com.edwin.data.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMediaDetailsByIdUseCase @Inject constructor(
    private val repository: MediaRepository
) {

    operator fun invoke(mediaId: Int): Flow<NetworkResponse<MediaDetails>> {
        return repository.getMediaById(mediaId)
    }
}
