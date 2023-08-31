package com.example.myweather.search

import androidx.compose.ui.text.input.TextFieldValue
import com.example.myweather.base.UiEffect
import com.example.myweather.base.UiEvent
import com.example.myweather.base.UiState

class SearchContract {
    data class State(
        val searchTextField: TextFieldValue
    ) : UiState

    sealed class Event : UiEvent {
        data class UpdateTextFieldValue(
            val text: TextFieldValue
        ) : Event()
    }

    object Effect : UiEffect
}