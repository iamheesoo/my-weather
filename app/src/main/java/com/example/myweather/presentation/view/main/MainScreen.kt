package com.example.myweather.presentation.view.main

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.myweather.presentation.view.NavigationKey
import com.example.myweather.R
import com.example.myweather.presentation.data.LatAndLon
import com.example.myweather.domain.local.LocationEntity
import com.example.myweather.presentation.extensions.onClick
import com.example.myweather.presentation.view.info.WeatherInfoScreen
import com.example.myweather.presentation.view.info.WeatherInfoViewModel
import com.example.myweather.presentation.utils.floorUnder4
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.orhanobut.logger.Logger
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@SuppressLint("MissingPermission")
@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalFoundationApi::class
)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onListClick: (List<LocationEntity>) -> Unit,
    navController: NavController
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val locationList = uiState.value.locationList
    val currentLocation = uiState.value.currentLocation

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        if (locationList?.isNotEmpty() == true) locationList.size else 1
    }
    val permissionList = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ),
        onPermissionsResult = {}
    )

    val isAdded =
        navController.currentBackStackEntry?.savedStateHandle?.getStateFlow(
            NavigationKey.IS_ADDED,
            false
        )
            ?.collectAsStateWithLifecycle()
    LaunchedEffect(
        isAdded?.value != null
    ) {
        if (isAdded?.value == true) {
            viewModel.sendEvent(MainContract.Event.UpdateLocationList)
        }
    }

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.onEach { _effect ->
            when (_effect) {
                is MainContract.Effect.GoToLastPage -> {
                    coroutineScope {
                        delay(500L)
                        pagerState.scrollToPage(pagerState.pageCount - 1)
                    }
                }
            }
        }.collect()
    }

    Scaffold(
        bottomBar = {
            BottomBar(
                pagerState = pagerState,
                onListClick = { onListClick.invoke(locationList ?: emptyList()) })
        }
    ) { innerPadding ->
        if (permissionList.allPermissionsGranted) {
            val client = LocationServices.getFusedLocationProviderClient(LocalContext.current)
            client.lastLocation
                .addOnSuccessListener {
                    viewModel.sendEvent(
                        MainContract.Event.UpdateCurrentLocation(
                            location = LatAndLon(
                                latitude = it.latitude.floorUnder4(),
                                longitude = it.longitude.floorUnder4()
                            )
                        )
                    )
                }
                .addOnFailureListener {
                    Logger.d("!!! location fail ${it.message}")
                }
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                HorizontalPager(
                    state = pagerState,
                ) { currentPage ->
                    (locationList?.getOrNull(currentPage)?.latAndLon
                        ?: currentLocation)?.let { _pageLocation ->
                        WeatherInfoScreen(
                            location = _pageLocation,
                            viewModel = hiltViewModel<WeatherInfoViewModel>(),
                            mainViewModel = hiltViewModel<MainViewModel>()
                        )
                    }
                }
            }
        } else {
            permissionList.launchMultiplePermissionRequest()
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomBar(pagerState: PagerState, onListClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(color = Color(0xFF8399b0))
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

@OptIn(ExperimentalFoundationApi::class)
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
        repeat(pagerState.pageCount - 1) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_circle_24),
                contentDescription = "circle",
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(14.dp),
                tint = if (pagerState.currentPage - 1 == it) Color.White else Color.LightGray
            )
        }
    }
}