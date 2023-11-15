package com.example.myweather.presentation.view.main

import com.example.myweather.presentation.base.UiEffect
import com.example.myweather.presentation.base.UiEvent
import com.example.myweather.presentation.base.UiState
import com.example.myweather.presentation.data.LatAndLon
import com.example.myweather.domain.local.LocationEntity
import com.example.myweather.data.response.WeatherResponse

class MainContract {
    data class State(
        val weather: WeatherResponse? = null,
        val currentLocation: LatAndLon? = null,
        val locationList: List<LocationEntity>? = null
    ) : UiState

    sealed class Event: UiEvent {
        data class UpdateCurrentLocation(
            val location: LatAndLon
        ): Event()

        object UpdateLocationList: Event()
    }

    sealed class Effect: UiEffect {
        object GoToLastPage: Effect()
    }
}
