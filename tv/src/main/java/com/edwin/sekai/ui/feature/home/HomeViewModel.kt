package com.edwin.sekai.ui.feature.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.edwin.sekai.ui.feature.home.model.TabNavOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    @OptIn(SavedStateHandleSaveableApi::class)
    var selectedTab by savedStateHandle.saveable { mutableStateOf(TabNavOption.Home) }
        private set

    fun setTab(tabNavOption: TabNavOption) {
        selectedTab = tabNavOption
    }
}