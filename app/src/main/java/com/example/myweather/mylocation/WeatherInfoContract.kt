package com.example.myweather.mylocation

import com.example.myweather.base.UiEffect
import com.example.myweather.base.UiEvent
import com.example.myweather.base.UiState
import com.example.myweather.domain.HourlyData
import com.example.myweather.domain.WeatherHourlyResponse
import com.example.myweather.domain.WeatherResponse

class WeatherInfoContract {

    data class State(
        val weather: WeatherResponse? = null,
        val weatherHourly: WeatherHourlyResponse? = null,
        val weatherHourlyList: List<HourlyData>? = null
    ): UiState

    sealed class Event: UiEvent {
        object RequestWeatherInfo: Event()
    }

    sealed class Effect: UiEffect
}
