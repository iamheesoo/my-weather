package com.example.myweather.presentation.view.search

import androidx.compose.ui.text.input.TextFieldValue
import com.example.myweather.presentation.base.UiEffect
import com.example.myweather.presentation.base.UiEvent
import com.example.myweather.presentation.base.UiState
import com.example.myweather.data.response.GeocodingData

class SearchContract {
    data class State(
        val searchTextField: TextFieldValue,
        val geocodingList: List<GeocodingData>?,
        val isLoading: Boolean,
        val isAdded: Boolean,
    ) : UiState

    sealed class Event : UiEvent {
        data class UpdateTextFieldValue(
            val text: TextFieldValue
        ) : Event()
        object ClickOnSearch: Event()
        data class ClickOnGeocoding(
            val data: GeocodingData
        ): Event()
    }

    sealed class Effect : UiEffect {
        data class ShowToast(
            val text: String
        ): Effect()
    }
}