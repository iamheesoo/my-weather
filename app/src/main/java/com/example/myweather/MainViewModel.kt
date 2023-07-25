package com.example.myweather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweather.domain.ApiState
import com.example.myweather.domain.WeatherRepository
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    var location: LatAndLong? = null

    var uiState = MutableStateFlow<MainUiState>(MainUiState())
    private set

    fun requestGetWeather() {
        location?.let { _location ->
            viewModelScope.launch(Dispatchers.IO) {
                weatherRepository.getWeather(lat = _location.latitude, lon = _location.longitude)
                    .collectLatest {
                        when(it) {
                            is ApiState.Success -> {
                                Logger.d("success ${it.data}")
                                uiState.update { currentState ->
                                    currentState.copy(weather = it.data)
                                }
                            }
                            is ApiState.Error -> {
                                // todo error 처리
                                Logger.d("error ${it.data}")
                            }
                        }
                    }
            }

        }

    }
}