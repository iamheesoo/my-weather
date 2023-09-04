package com.example.myweather

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myweather.data.LatAndLong
import com.example.myweather.extensions.onClick
import com.example.myweather.info.WeatherInfoScreen
import com.example.myweather.info.WeatherInfoViewModel
import com.example.myweather.ui.theme.Azure
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.orhanobut.logger.Logger

@SuppressLint("MissingPermission")
@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onListClick: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val locationList = uiState.value.locationList

    val pagerState = rememberPagerState()
    val permissionList = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ),
        onPermissionsResult = {}
    )

    Scaffold(
        bottomBar = {
            BottomBar(pagerState = pagerState, onListClick = onListClick)
        }
    ) { innerPadding ->
        if (permissionList.allPermissionsGranted) {
            val client = LocationServices.getFusedLocationProviderClient(LocalContext.current)
            client.lastLocation
                .addOnSuccessListener {
                    viewModel.sendEvent(
                        MainContract.Event.UpdateCurrentLocation(
                            location = LatAndLong(latitude = it.latitude, longitude = it.longitude)
                        )
                    )
                }
                .addOnFailureListener {
                    Logger.d("!!! location fail ${it.message}")
                }
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
            ) {
                HorizontalPager(count = 2, state = pagerState) {
                    Column {
                        val myLocation = if (currentPage == 0) {
                            uiState.value.currentLocation
                        } else {
                            locationList?.getOrNull(currentPage - 1)?.let { data ->
                                LatAndLong(
                                    latitude = data.latitude,
                                    longitude = data.longitude
                                )
                            } ?: run {
                                // newYork
                                LatAndLong(40.776676, -73.971321)
                            }
                        }
                        Logger.d("!!! MainScreen currentLocation $myLocation")
                        myLocation?.let { _location ->
                            val weatherInfoViewModel: WeatherInfoViewModel =
                                hiltViewModel<WeatherInfoViewModel>().apply {
                                    setMyLocation(_location)
                                }
                            Logger.d("!!! MainScreen let ${_location}")
                            WeatherInfoScreen(
                                location = _location,
                                viewModel = weatherInfoViewModel
                            )
                        }
                    }
                }
            }
        } else {
            permissionList.launchMultiplePermissionRequest()
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun BottomBar(pagerState: PagerState, onListClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(color = Azure)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.outline_map_24),
            contentDescription = "map",
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterStart),
            tint = Color.White
        )
        PagerIndicator(modifier = Modifier.align(Alignment.Center), pagerState = pagerState)
        Icon(
            painter = painterResource(id = R.drawable.baseline_format_list_bulleted_24),
            contentDescription = "list",
            modifier = Modifier
                .onClick { onListClick.invoke() }
                .size(24.dp)
                .align(Alignment.CenterEnd),
            tint = Color.White
        )
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerIndicator(modifier: Modifier, pagerState: PagerState) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_near_me_24),
            contentDescription = "arrow",
            modifier = Modifier.size(20.dp),
            tint = if (pagerState.currentPage == 0) Color.White else Color.LightGray
        )
        Icon(
            painter = painterResource(id = R.drawable.baseline_circle_24),
            contentDescription = "circle",
            modifier = Modifier
                .padding(start = 8.dp)
                .size(14.dp),
            tint = if (pagerState.currentPage == 1) Color.White else Color.LightGray
        )
    }
}