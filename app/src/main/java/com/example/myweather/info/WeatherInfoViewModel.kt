package com.example.myweather.info

import androidx.lifecycle.viewModelScope
import com.example.myweather.base.BaseMviViewModel
import com.example.myweather.data.LatAndLong
import com.example.myweather.domain.ApiState
import com.example.myweather.repository.LocationRepository
import com.example.myweather.repository.WeatherRepository
import com.example.myweather.utils.dtTxtToLong
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : BaseMviViewModel<WeatherInfoContract.State, WeatherInfoContract.Event, WeatherInfoContract.Effect>() {

    var location: LatAndLong? = null

    fun setMyLocation(location: LatAndLong) {
        if (!isMapContainsLocation(location)) {
            setState {
                copy(
                    hashMap = HashMap(hashMap).apply { put(location, LocationInfo()) }
                )
            }
            this.location = location
        }
    }

    override fun createState(): WeatherInfoContract.State {
        Logger.d("!!! createState")
        return WeatherInfoContract.State(
            hashMap = hashMapOf()
        )
    }

    override fun initialState() {
        Logger.d("!!! initialState")

    }

    override fun loadData() {
        Logger.d("!!! loadData")
//        location?.let {
//            requestGetWeather(it)
//        }

    }

    override fun handleEvent(event: WeatherInfoContract.Event) {
        when (event) {
            WeatherInfoContract.Event.RequestWeatherInfo -> {
                location?.let { _location ->
                    requestGetWeather(_location)
                    requestGetWeatherHourly(_location)
                    requestGetAirPollution(_location)
                }
            }
        }
    }

    private fun requestGetWeather(location: LatAndLong) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getWeather(lat = location.latitude, lon = location.longitude)
                .collectLatest {
                    when (it) {
                        is ApiState.Success -> {
                            setState {
                                copy(
                                    hashMap = HashMap(hashMap).apply {
                                        put(location, get(location)?.copy(weather = it.data))
                                    }
                                )
                            }
                        }

                        is ApiState.Error -> {
                            // todo error 처리
                            Logger.d("!!! error ${it.data}")
                        }
                    }
                }


        }
    }

    private fun requestGetWeatherHourly(location: LatAndLong) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getWeatherHourly(lat = location.latitude, lon = location.longitude)
                .collectLatest {
                    when (it) {
                        is ApiState.Success -> {
                            setState {
                                copy(
                                    hashMap = HashMap(hashMap).apply {
                                        put(location, get(location)?.copy(weatherHourly = it.data))
                                    }
                                )
                            }
                            val currentTime = System.currentTimeMillis()
                            val list = it.data?.list
                                ?.filter {
                                    (it.dtTxt?.dtTxtToLong() ?: 0L) >= currentTime
                                }
                                ?.take(10)
                            setState {
                                copy(
                                    hashMap = HashMap(hashMap).apply {
                                        put(location, get(location)?.copy(weatherHourlyList = list))
                                    }
                                )
                            }
                        }

                        is ApiState.Error -> {
                            // todo error 처리
                            Logger.d("!!! error ${it.data}")
                        }
                    }
                }
        }
    }

    private fun requestGetAirPollution(location: LatAndLong) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getAirPollution(lat = location.latitude, lon = location.longitude)
                .collectLatest {
                    when (it) {
                        is ApiState.Success -> {
                            setState {
                                copy(
                                    hashMap = HashMap(hashMap).apply {
                                        put(location, get(location)?.copy(airPollution = it.data))
                                    }
                                )
                            }
                        }

                        is ApiState.Error -> {
                            // todo error 처리
                            Logger.d("!!! error ${it.data}")
                        }
                    }
                }
        }
    }

    fun isMapContainsLocation(location: LatAndLong): Boolean {
        val key =  state.hashMap.keys.find { it.latitude == location.latitude && it.longitude == location.longitude }
        val value = state.hashMap.get(key)
        return value?.weather != null
    }
}