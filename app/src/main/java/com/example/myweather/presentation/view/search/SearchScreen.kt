package com.example.myweather.presentation.view.search

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myweather.presentation.view.NavigationKey
import com.example.myweather.R
import com.example.myweather.presentation.composable.GeocodingItem
import com.example.myweather.presentation.composable.LocationItem
import com.example.myweather.domain.local.LocationEntity
import com.example.myweather.presentation.theme.PlaceHolder
import com.example.myweather.presentation.theme.PrimaryTextColor
import com.example.myweather.presentation.theme.SubTitle1
import com.example.myweather.presentation.theme.SubTitle3
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(onPopBackStack: (Boolean) -> Unit, navController: NavController) {
    val context = LocalContext.current
    val viewModel = hiltViewModel<SearchViewModel>()
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val searchTextField = state.value.searchTextField
    val geocodingList = state.value.geocodingList
    val isLoading = state.value.isLoading

    val keyboardController = LocalSoftwareKeyboardController.current

    val locationList = navController.previousBackStackEntry?.savedStateHandle?.getStateFlow(
        NavigationKey.LOCATION_LIST,
        emptyList<LocationEntity>()
    )?.collectAsStateWithLifecycle()?.value

    BackHandler(enabled = true) {
        onPopBackStack.invoke(state.value.isAdded)
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.onEach { _effect ->
            when (_effect) {
                is SearchContract.Effect.ShowToast -> {
                    Toast.makeText(context, _effect.text, Toast.LENGTH_SHORT).show()
                }
            }
        }.collect()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(horizontal = 16.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(30.dp)
                        .border(width = 2.dp, color = Color.White, shape = CircleShape)
                        .padding(3.dp)
                        .align(Alignment.TopEnd),
                    painter = painterResource(id = R.drawable.round_more_horiz_24),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = Color.White),
                )
            }
        }

        item {
            Text(
                text = "날씨",
                color = PrimaryTextColor,
                style = SubTitle1
            )
        }

        item {
            TextField(
                value = searchTextField,
                onValueChange = {
                    viewModel.sendEvent(SearchContract.Event.UpdateTextFieldValue(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, bottom = 10.dp),
                shape = RoundedCornerShape(8.dp),
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.round_search_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.LightGray)
                    )
                },
                placeholder = {
                    Text("도시 검색", color = Color.LightGray, style = PlaceHolder)
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.DarkGray,
                    unfocusedContainerColor = Color.DarkGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.LightGray,
                    unfocusedTextColor = Color.LightGray
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                        viewModel.sendEvent(SearchContract.Event.ClickOnSearch)
                    }
                )
            )
        }

        if (!geocodingList.isNullOrEmpty()) {
            items(geocodingList.size) { index ->
                GeocodingItem(
                    koCity = geocodingList[index].localNames?.ko ?: "",
                    enCity = geocodingList[index].localNames?.en ?: "",
                    lat = geocodingList[index].lat ?: 0.0,
                    lon = geocodingList[index].lon ?: 0.0,
                    onItemClick = {
                        Logger.d("!!!onItemClick")
                        viewModel.sendEvent(
                            SearchContract.Event.ClickOnGeocoding(geocodingList[index])
                        )
                    }
                )
            }
        } else if (isLoading) {
            item {
                CircularProgressIndicator(
                    color = Color.White
                )
            }
        } else if (locationList?.isNotEmpty() == true) {
            items(locationList.size) { index ->
                LocationItem(
                    locationEntity = locationList[index],
                    isCurrentLocation = index == 0
                )
            }
        } else {
            item {
                Text(
                    text = "데이터 없음",
                    color = PrimaryTextColor,
                    style = SubTitle3
                )
            }
        }

    }
}