package com.example.myweather.mylocation

import androidx.lifecycle.viewModelScope
import com.example.myweather.base.BaseMviViewModel
import com.example.myweather.data.LatAndLong
import com.example.myweather.domain.ApiState
import com.example.myweather.domain.WeatherRepository
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WeatherInfoViewModel(
    val location: LatAndLong,
    private val weatherRepository: WeatherRepository
): BaseMviViewModel<WeatherInfoContract.State, WeatherInfoContract.Event, WeatherInfoContract.Effect>() {

    init {
        // loadData
        requestGetWeather(location)
    }

    override fun createInitialState(): WeatherInfoContract.State {
        return WeatherInfoContract.State(

        )
    }

    override fun handleEvent(event: WeatherInfoContract.Event) {
        when (event) {
            else -> {}
        }
    }

    private fun requestGetWeather(location: LatAndLong) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getWeather(lat = location.latitude, lon = location.longitude)
                .collectLatest {
                    when (it) {
                        is ApiState.Success -> {
                            Logger.d("success ${it.data}")
                            setState {
                                copy(weather = it.data)
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