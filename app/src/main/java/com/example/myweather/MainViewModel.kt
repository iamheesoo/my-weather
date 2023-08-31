package com.example.myweather

import com.example.myweather.base.BaseMviViewModel
import com.example.myweather.data.LatAndLong
import com.example.myweather.domain.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject constructor(
    private val weatherRepository: WeatherRepository
) : BaseMviViewModel<MainContract.State, MainContract.Event, MainContract.Effect>() {
    var locationMap: HashMap<Int, LatAndLong> = hashMapOf()


    override fun createState(): MainContract.State {
        return MainContract.State(

        )
    }

    override fun initialState() {
    }

    override fun loadData() {
    }

    override fun handleEvent(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.UpdateCurrentLocation -> {
//                locationMap[event.pageIndex] = event.location
//                requestGetWeather(event.location)
                setState {
                    copy(currentLocation = event.location)
                }
            }

            else -> {}
        }


    }

    /*
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

     */
}