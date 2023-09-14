package com.example.myweather

import com.example.myweather.base.UiEffect
import com.example.myweather.base.UiEvent
import com.example.myweather.base.UiState
import com.example.myweather.data.LatAndLon
import com.example.myweather.database.LocationEntity
import com.example.myweather.domain.WeatherResponse

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

        object BackHandler: Event()
    }

    sealed class Effect: UiEffect {
        object GoToLastPage: Effect()
    }
}
