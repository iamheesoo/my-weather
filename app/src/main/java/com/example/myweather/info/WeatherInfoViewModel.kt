package com.example.myweather.info

import androidx.lifecycle.viewModelScope
import com.example.myweather.base.BaseMviViewModel
import com.example.myweather.data.LatAndLong
import com.example.myweather.repository.WeatherRepository
import com.example.myweather.utils.dtTxtToLong
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : BaseMviViewModel<WeatherInfoContract.State, WeatherInfoContract.Event, WeatherInfoContract.Effect>() {

    var location: LatAndLong? = null
    private val locationInfo = LocationInfo()

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
                    requestMultipleApi(_location)
                }
            }
        }
    }

    private fun requestMultipleApi(location: LatAndLong) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                val weatherTask = async {
                    weatherRepository.getWeather(
                        lat = location.latitude,
                        lon = location.longitude
                    )
                }
                val weatherHourlyTask = async {
                    weatherRepository.getWeatherHourly(
                        lat = location.latitude,
                        lon = location.longitude
                    )
                }
                val airPollutionTask = async {
                    weatherRepository.getAirPollution(
                        lat = location.latitude,
                        lon = location.longitude
                    )
                }

                combine(
                    weatherTask.await(),
                    weatherHourlyTask.await(),
                    airPollutionTask.await()
                ) { weatherResponse, weatherHourlyResponse, airPollutionResponse ->
                    LocationInfo(
                        weather = weatherResponse.data,
                        weatherHourly = weatherHourlyResponse.data,
                        weatherHourlyList = weatherHourlyResponse.data?.list
                            ?.filter {
                                val currentTime = System.currentTimeMillis()
                                (it.dtTxt?.dtTxtToLong() ?: 0L) >= currentTime
                            }
                            ?.take(10),
                        airPollution = airPollutionResponse.data
                    ).also {
                        Logger.d("!!! requestMultipleApi LocationInfo $it")
                    }
                }.collectLatest { _locationInfo ->
                    setState {
                        copy(
                            hashMap = HashMap(hashMap).apply {
                                put(location, _locationInfo)
                            }
                        )
                    }
                }
            }
        }
    }

    fun isMapContainsLocation(location: LatAndLong): Boolean {
        val key =
            state.hashMap.keys.find { it.latitude == location.latitude && it.longitude == location.longitude }
        val value = state.hashMap.get(key)
        return value?.weather != null
    }
}