package com.example.myweather

import androidx.lifecycle.viewModelScope
import com.example.myweather.base.BaseMviViewModel
import com.example.myweather.data.LatAndLong
import com.example.myweather.repository.LocationRepository
import com.example.myweather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
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
                setState {
                    copy(currentLocation = event.location)
                }
            }

            is MainContract.Event.BackHandler -> {
                getLocationList()
            }
        }
    }

    private fun getLocationList() {
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.getLocationData()
                .collectLatest {
                    setState {
                        copy(locationList = it)
                    }
                }
        }
    }
}