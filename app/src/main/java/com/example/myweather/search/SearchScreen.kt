package com.example.myweather.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myweather.R
import com.example.myweather.composable.GeocodingItem
import com.example.myweather.ui.theme.PrimaryTextColor
import com.example.myweather.ui.theme.SubTitle1

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun SearchScreen() {
    val viewModel = hiltViewModel<SearchViewModel>()//koinViewModel<SearchViewModel>()
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val searchTextField = state.value.searchTextField
    val geocodingList = state.value.geocodingList
    val isLoading = state.value.isLoading

    val keyboardController = LocalSoftwareKeyboardController.current

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
                    .padding(10.dp)
            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp)
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
                    .padding(10.dp),
                shape = RoundedCornerShape(8.dp),
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.round_search_24),
                        contentDescription = null
                    )
                },
                placeholder = {
                    Text("도시 검색", color = Color.LightGray)
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
        }

    }
}