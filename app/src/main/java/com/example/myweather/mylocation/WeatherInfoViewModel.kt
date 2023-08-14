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
    private val weatherRepository: WeatherRepository
): BaseMviViewModel<WeatherInfoContract.State, WeatherInfoContract.Event, WeatherInfoContract.Effect>() {


    var location: LatAndLong? = null
    init {
        Logger.d("WeatherInfoViewModel location $location")
    }


    override fun createState(): WeatherInfoContract.State {
        Logger.d("createState")
        return WeatherInfoContract.State(

        )
    }

    override fun initialState() {
        Logger.d("initialState")

    }

    override fun loadData() {
        Logger.d("loadData")
//        location?.let {
//            requestGetWeather(it)
//        }

    }

    override fun handleEvent(event: WeatherInfoContract.Event) {
        Logger.d("handleEvent")
        when (event) {
            WeatherInfoContract.Event.RequestWeatherInfo -> {
                location?.let { _location ->
                    requestGetWeather(_location)
                }
            }
            else -> {}
        }
    }

    private fun requestGetWeather(location: LatAndLong) {
        Logger.d("requestGetWeather")
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