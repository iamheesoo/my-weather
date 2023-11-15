package com.example.myweather.presentation.view.search

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.example.myweather.presentation.base.BaseMviViewModel
import com.example.myweather.presentation.data.LatAndLon
import com.example.myweather.domain.local.LocationEntity
import com.example.myweather.data.network.ApiState
import com.example.myweather.data.response.GeocodingData
import com.example.myweather.domain.repository.GeocodingRepository
import com.example.myweather.domain.repository.LocationRepository
import com.example.myweather.presentation.utils.floorUnder4
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
            isLoading = false,
            isAdded = false
        )
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
        viewModelScope.launch(Dispatchers.IO) {
            locationRepository.addLocationData(
                data.toLocationEntity()
            ).collectLatest {
                setState {
                    copy(isAdded = it)
                }
                sendEffect {
                    SearchContract.Effect.ShowToast("도시가 추가되었습니다.")
                }
            }
        }
    }

    private fun GeocodingData.toLocationEntity() =
        LocationEntity(
            latAndLon = LatAndLon(
                latitude = lat?.floorUnder4() ?: 0.0,
                longitude = lon?.floorUnder4() ?: 0.0
            ),
            name = name ?: ""
        )

}