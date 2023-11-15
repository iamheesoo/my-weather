package com.example.myweather.presentation.view.info

import com.example.myweather.presentation.base.UiEffect
import com.example.myweather.presentation.base.UiEvent
import com.example.myweather.presentation.base.UiState
import com.example.myweather.presentation.data.LatAndLon
import com.example.myweather.data.response.AirPollutionResponse
import com.example.myweather.data.response.HourlyData
import com.example.myweather.data.response.WeatherHourlyResponse
import com.example.myweather.data.response.WeatherResponse

class WeatherInfoContract {

    data class State(
        val hashMap: HashMap<LatAndLon, LocationInfo>,
        val isLoading: Boolean
    ): UiState

    sealed class Event: UiEvent {
        object RequestWeatherInfo: Event()
        data class UpdateMyLocation(
            val latAndLon: LatAndLon
        ): Event()
    }

    sealed class Effect: UiEffect {
        object UpdateLocationList: Effect()
    }
}

data class LocationInfo(
    val weather: WeatherResponse? = null,
    val weatherHourly: WeatherHourlyResponse? = null,
    val weatherHourlyList: List<HourlyData>? = null,
    val airPollution: AirPollutionResponse? = null
)
