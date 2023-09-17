package com.example.myweather.info

import androidx.lifecycle.viewModelScope
import com.example.myweather.base.BaseMviViewModel
import com.example.myweather.data.LatAndLon
import com.example.myweather.database.LocationEntity
import com.example.myweather.repository.LocationRepository
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
import kotlin.math.roundToInt

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) : BaseMviViewModel<WeatherInfoContract.State, WeatherInfoContract.Event, WeatherInfoContract.Effect>() {

    var location: LatAndLon? = null
    private set

    private fun setLocation(location: LatAndLon) {
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
            is WeatherInfoContract.Event.RequestWeatherInfo -> {
                location?.let { _location ->
                    requestMultipleApi(_location)
                }
            }
            is WeatherInfoContract.Event.UpdateMyLocation -> {
                setLocation(event.latAndLon)
            }
        }
    }

    private fun requestMultipleApi(location: LatAndLon) {
        viewModelScope.launch(Dispatchers.IO) {
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
                val currentTime = System.currentTimeMillis()
                LocationInfo(
                    weather = weatherResponse.data,
                    weatherHourly = weatherHourlyResponse.data,
                    weatherHourlyList = weatherHourlyResponse.data?.list
                        ?.filter {
                            (it.dtTxt?.dtTxtToLong() ?: 0L) >= currentTime
                        }
                        ?.take(10),
                    airPollution = airPollutionResponse.data
                )
            }.collectLatest { _locationInfo ->
                /**
                 * db에 온도 등 날씨 정보 갱신
                 */
                locationRepository.insertOrUpdate(_locationInfo.toLocationEntity())
                    .collectLatest { isSuccess ->
                        Logger.d("!!! WeatherInfoViewModel UPDATE $isSuccess")
                    }
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

    fun isMapContainsLocation(location: LatAndLon): Boolean {
        val key =
            state.hashMap.keys.find { it.latitude == location.latitude && it.longitude == location.longitude }
        val value = state.hashMap.get(key)
        return value?.weather != null
    }

    private fun LocationInfo.toLocationEntity() = LocationEntity(
        latAndLon = LatAndLon(
            latitude = this.weather?.coord?.lat ?: 0.0,
            longitude = this.weather?.coord?.lon ?: 0.0
        ),
        name = this.weather?.name ?: "",
        temp = this.weather?.main?.temp?.roundToInt() ?: 0,
        tempMax = this.weather?.main?.tempMax?.roundToInt() ?: 0,
        tempMin = this.weather?.main?.tempMin?.roundToInt() ?: 0,
        weatherInfo = this.weather?.weatherList?.firstOrNull()?.description ?: "",
        timezone = this.weather?.timezone ?: 0L
    )
}