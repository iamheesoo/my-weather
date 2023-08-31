package com.example.myweather.search

import androidx.compose.ui.text.input.TextFieldValue
import com.example.myweather.base.UiEffect
import com.example.myweather.base.UiEvent
import com.example.myweather.base.UiState
import com.example.myweather.domain.GeocodingData

class SearchContract {
    data class State(
        val searchTextField: TextFieldValue,
        val geocodingList: List<GeocodingData>?,
        val isLoading: Boolean
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

    object Effect : UiEffect
}