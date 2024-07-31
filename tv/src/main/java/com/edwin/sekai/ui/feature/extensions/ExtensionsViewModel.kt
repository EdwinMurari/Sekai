package com.edwin.sekai.ui.feature.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwin.data.model.Extension
import com.edwin.data.repository.ExtensionsRepository
import com.edwin.extension_manager.ExtensionInstallManager
import com.edwin.sekai.ui.feature.extensions.mapper.asUiModel
import com.edwin.sekai.ui.feature.extensions.model.ExtensionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ExtensionsViewModel @Inject constructor(
    private val repository: ExtensionsRepository,
    private val extensionInstallManager: ExtensionInstallManager,
) : ViewModel() {

    val uiState: StateFlow<ExtensionsUiState> = repository.getExtensions()
        .map<List<Extension>, ExtensionsUiState> { availableExtensionList ->
            ExtensionsUiState.Success(
                extensions = availableExtensionList
                    .map(Extension::asUiModel)
                    .sortedWith(
                        compareByDescending<ExtensionUiModel> {
                            it is ExtensionUiModel.Installed
                        }.thenByDescending {
                            if (it is ExtensionUiModel.Installed) it.hasUpdate else false
                        }.thenBy {
                            it.language.lowercase() != "all"
                        }.thenBy {
                            it.language.lowercase() != "english"
                        }.thenByDescending {
                            it.language
                        }
                    )
            )
        }
        .catch {
            emit(ExtensionsUiState.Error)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ExtensionsUiState.Loading,
        )

    private val _selectedExtension = MutableStateFlow<ExtensionUiModel.Installed?>(null)
    val selectedExtension: StateFlow<ExtensionUiModel.Installed?> = _selectedExtension.asStateFlow()

    fun onExtensionClick(extensionUiModel: ExtensionUiModel) {
        when (extensionUiModel) {
            is ExtensionUiModel.Available -> installExtension(extensionUiModel)
            is ExtensionUiModel.Installed -> viewExtension(extensionUiModel)
        }
    }

    private fun viewExtension(extensionUiModel: ExtensionUiModel.Installed) {
        _selectedExtension.value = extensionUiModel
    }

    fun dismissViewExtension() {
        _selectedExtension.value = null
    }

    private fun installExtension(extensionUiModel: ExtensionUiModel.Available) {
        extensionInstallManager.downloadAndInstallExtension(
            apkUrl = extensionUiModel.apkUrl,
            name = extensionUiModel.title
        )
    }

    fun onClickUpdate(extensionUiModel: ExtensionUiModel.Installed) {
        extensionUiModel.apkUrl?.let {
            extensionInstallManager.updateExtension(
                apkUrl = it,
                name = extensionUiModel.title,
                pkgName = extensionUiModel.pkgName
            )
        }
    }

    fun onClickUninstall(extensionUiModel: ExtensionUiModel.Installed) {
        extensionInstallManager.uninstallExtension(pkgName = extensionUiModel.pkgName)
        dismissViewExtension()
    }
}

sealed interface ExtensionsUiState {

    data class Success(
        val extensions: List<ExtensionUiModel>
    ) : ExtensionsUiState

    data object Error : ExtensionsUiState
    data object Loading : ExtensionsUiState
}