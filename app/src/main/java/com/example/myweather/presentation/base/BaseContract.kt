package com.example.myweather.presentation.base

class BaseContract {
    enum class LoadState {
        LOADING,
        COMPLETE,
        ERROR,
        REFRESH,
        NONE
    }
    data class LoadingState(
        val loadState: LoadState
    ): UiState

    sealed class LoadEvent: UiEvent {
        object LoadData: LoadEvent()
        object Refresh: LoadEvent()
        object Loading: LoadEvent()
        object Complete: LoadEvent()
        object Error: LoadEvent()
    }

    sealed class LoadEffect: UiEffect {

    }
}