package com.example.myweather.mylocation

import com.example.myweather.base.UiEffect
import com.example.myweather.base.UiEvent
import com.example.myweather.base.UiState
import com.example.myweather.domain.WeatherResponse

class WeatherInfoContract {

    data class State(
        val weather: WeatherResponse? = null
    ): UiState

    sealed class Event: UiEvent

    sealed class Effect: UiEffect
}
