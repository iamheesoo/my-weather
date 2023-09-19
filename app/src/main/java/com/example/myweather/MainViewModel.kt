package com.example.myweather

import androidx.lifecycle.viewModelScope
import com.example.myweather.base.BaseMviViewModel
import com.example.myweather.repository.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : BaseMviViewModel<MainContract.State, MainContract.Event, MainContract.Effect>() {

    private var isBackHandler = false

    init {
        getLocationList()
    }


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
                if (state.currentLocation == null) {
                    setState {
                        copy(currentLocation = event.location)
                    }
                    getLocationList()
                }
            }

            is MainContract.Event.UpdateLocationList -> {
                isBackHandler = true
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
                    if (isBackHandler) {
                        sendEffect {
                            MainContract.Effect.GoToLastPage
                        }
                        isBackHandler = false
                    }
                }
        }
    }
}