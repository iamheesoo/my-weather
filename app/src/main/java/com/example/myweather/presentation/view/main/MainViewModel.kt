package com.example.myweather.presentation.view.main

import androidx.lifecycle.viewModelScope
import com.example.myweather.presentation.base.BaseMviViewModel
import com.example.myweather.domain.repository.LocationRepository
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

    override fun handleEvent(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.UpdateCurrentLocation -> {
                if (state.currentLocation != event.location) {
                    setState {
                        copy(currentLocation = event.location)
                    }
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