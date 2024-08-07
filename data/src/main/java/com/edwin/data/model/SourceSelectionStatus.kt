package com.edwin.data.model

sealed class SourceSelectionStatus {
    data object Loading : SourceSelectionStatus()
    data object SourceUnavailable : SourceSelectionStatus()
    data object SourceSelected : SourceSelectionStatus()
}