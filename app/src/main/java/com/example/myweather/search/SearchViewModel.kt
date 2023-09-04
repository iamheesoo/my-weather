package com.example.myweather.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.example.myweather.base.BaseMviViewModel
import com.example.myweather.database.LocationDao
import com.example.myweather.database.LocationEntity
import com.example.myweather.domain.ApiState
import com.example.myweather.domain.GeocodingData
import com.example.myweather.repository.GeocodingRepository
import com.example.myweather.repository.LocationRepository
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val geocodingRepository: GeocodingRepository,
    private val locationRepository: LocationRepository
) :
    BaseMviViewModel<SearchContract.State, SearchContract.Event, SearchContract.Effect>() {
    override fun createState(): SearchContract.State {
        return SearchContract.State(
            searchTextField = TextFieldValue(),
            geocodingList = null,
            isLoading = false
        )
    }

    override fun initialState() {
    }

    override fun loadData() {
    }

    override fun handleEvent(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.UpdateTextFieldValue -> {
                setState {
                    copy(searchTextField = event.text)
                }
            }
            is SearchContract.Event.ClickOnSearch -> {
                requestGetGeocoding()
            }
            is SearchContract.Event.ClickOnGeocoding -> {
                addLocation(event.data)
            }
        }
    }

    private fun requestGetGeocoding() {
        Logger.d("!!! requestGetGeocoding")
        viewModelScope.launch(Dispatchers.IO) {
            geocodingRepository.getGeocoding(state.searchTextField.text)
                .onStart {
                    setState {
                        copy(isLoading = true)
                    }
                }
                .onCompletion {
                    setState {
                        copy(isLoading = false)
                    }
                }
                .collectLatest {
                    when (it) {
                        is ApiState.Success -> {
                            Logger.d("!!! requestGetGeocoding success")
                            setState {
                                copy(
                                    geocodingList = it.data
                                )
                            }
                        }

                        is ApiState.Error -> {
                            // todo error 처리
                            Logger.d("!!! requestGetGeocoding error ${it.data}")
                        }
                    }
                }
        }
    }

    private fun addLocation(data: GeocodingData) {
        Logger.d("!!! addLocation")
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.addLocationData(
                data.toLocationEntity()
            ).collectLatest {
                Logger.d("!!! addLocation isSuccess $it")
            }
        }
    }

    private fun GeocodingData.toLocationEntity() =
        LocationEntity(
            latitude = lat ?: 0.0,
            longitude = lon ?: 0.0,
            name = name ?: ""
        )

}