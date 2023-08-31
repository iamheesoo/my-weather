package com.example.myweather

import com.example.myweather.base.BaseMviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseMviViewModel<MainContract.State, MainContract.Event, MainContract.Effect>() {
    override fun createState(): MainContract.State {
        return MainContract.State(
            currentLocation = null
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
        }
    }
}