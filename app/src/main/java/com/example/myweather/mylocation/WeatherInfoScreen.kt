package com.example.myweather.mylocation

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.times
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.myweather.R
import com.example.myweather.composable.AirPollutionContent
import com.example.myweather.composable.BigTitleContent
import com.example.myweather.composable.CustomTopAppBar
import com.example.myweather.composable.HourWeather
import com.example.myweather.composable.PressureContent
import com.example.myweather.composable.TodayWeather
import com.example.myweather.composable.TransparentColumn
import com.example.myweather.composable.VerticalGrid
import com.example.myweather.composable.VerticalGridContent
import com.example.myweather.composable.WindContent
import com.example.myweather.composable.weatherContent
import com.example.myweather.utils.buildExoPlayer
import com.example.myweather.utils.dtTxtToHour
import com.example.myweather.utils.getAirQualityInfo
import com.example.myweather.utils.getVideoUri
import com.example.myweather.utils.getWeatherIconDrawable
import kotlin.math.roundToInt

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun WeatherInfoScreen(
    viewModel: WeatherInfoViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val weather = state.value.weather
    val weatherHourlyList = state.value.weatherHourlyList
    val airPollution = state.value.airPollution

    val context = LocalContext.current
    val listState = rememberLazyListState()
    val videoUri = context.getVideoUri(R.raw.clouds)
    val exoPlayer = remember { context.buildExoPlayer(videoUri) }
    var customTopAppBarHeight by remember {
        mutableStateOf(400.dp)
    }
    val listFirstVisibleItemIndex by remember {
        derivedStateOf { listState.firstVisibleItemIndex }
    }

    val LAZY_COLUMN_EACH_PADDING = 20.dp

    LaunchedEffect(listFirstVisibleItemIndex) {
        customTopAppBarHeight = max(400.dp - (listFirstVisibleItemIndex.times(30.dp)), 100.dp)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        DisposableEffect(Unit) {
            onDispose {
                exoPlayer.release()
            }
        }

        AndroidView(
            factory = {
                PlayerView(it).apply {
                    player = exoPlayer
                    layoutParams = FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                }
            },
            modifier = Modifier.fillMaxSize()
        )
        if (weather == null) {
            viewModel.sendEvent(WeatherInfoContract.Event.RequestWeatherInfo)
            Text("WeatherInfoContract.Event.RequestWeatherInfo")
        } else {
            TransparentColumn(
                modifier = Modifier.fillMaxSize(),
                header = { _modifier ->
                    CustomTopAppBar(
                        modifier = _modifier,
                        height = customTopAppBarHeight,
                        locationName = weather.name,
                        temp = weather.main?.temp?.roundToInt(),
                        description = weather.weatherList?.firstOrNull()?.description,
                        tempMax = weather.main?.tempMax?.roundToInt(),
                        tempMin = weather.main?.tempMin?.roundToInt()
                    )
                },
                content = { _modifier ->
                    LazyColumn(
                        state = listState,
                        modifier = _modifier,
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        weatherContent(
                            titleIconId = R.drawable.round_access_alarm_24,
                            titleText = "시간별 일기예보",
                            content = { _modifier ->
                                if (weatherHourlyList?.isNotEmpty() == true) {
                                    LazyRow(
                                        modifier = _modifier,
                                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                                    ) {
                                        items(weatherHourlyList.size) { index ->
                                            HourWeather(
                                                hour = weatherHourlyList[index].dtTxt?.dtTxtToHour(),
                                                temp = weatherHourlyList[index].main?.temp?.roundToInt()
                                                    ?: 0,
                                                icon = context.getWeatherIconDrawable(
                                                    weatherHourlyList[index].weatherList?.firstOrNull()?.icon
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        )

                        item {
                            Spacer(modifier = Modifier.height(LAZY_COLUMN_EACH_PADDING))
                        }

                        weatherContent(
                            titleIconId = R.drawable.round_calendar_month_24,
                            titleText = "10일간의 일기예보",
                            content = { _modifier ->
                                TodayWeather(
                                    modifier = _modifier,
                                    icon = context.getWeatherIconDrawable(weather.weatherList?.firstOrNull()?.icon),
                                    tempMin = weather.main?.tempMin?.roundToInt(),
                                    tempMax = weather.main?.tempMax?.roundToInt()
                                )
                            }
                        )



                        item {
                            Spacer(modifier = Modifier.height(LAZY_COLUMN_EACH_PADDING))
                        }

                        if (airPollution?.list?.firstOrNull() != null) {
                            val item = airPollution.list.first()
                            weatherContent(
                                titleIconId = R.drawable.baseline_blur_on_24,
                                titleText = "대기질",
                                content = { _modifier ->
                                    AirPollutionContent(
                                        modifier = _modifier,
                                        aqi = item.main?.aqi ?: 1,
                                        content = getAirQualityInfo(item.main?.aqi)
                                    )
                                }
                            )
                        }


                        item {
                            Spacer(modifier = Modifier.height(LAZY_COLUMN_EACH_PADDING))
                        }

                        item {
                            VerticalGrid() {
                                VerticalGridItem.values().forEachIndexed { index, item ->
                                    VerticalGridContent(
                                        titleIconId = item.icon,
                                        titleText = item.title,
                                        paddingValues = PaddingValues(
                                            horizontal = 4.dp,
                                            vertical = 4.dp
                                        )
                                    ) { _modifier ->
                                        when (item) {
                                            VerticalGridItem.UV -> {
                                                Text(modifier = _modifier, text = "testtttt")
                                            }
                                            VerticalGridItem.WIND -> {
                                                WindContent(
                                                    modifier = _modifier,
                                                    speed = weather.wind?.speed ?: 0.0,
                                                    deg = weather.wind?.deg
                                                )
                                            }

                                            VerticalGridItem.RAINY -> {
                                                BigTitleContent(
                                                    modifier = _modifier,
                                                    title = if (weather.rain?.hour1 != null) {
                                                        "${weather.rain.hour1}mm"
                                                    } else {
                                                        null
                                                    }
                                                )
                                            }

                                            VerticalGridItem.TEMPERATURE -> {
                                                val feelsLike = weather.main?.feelsLike ?: 0.0
                                                val temp = weather.main?.temp ?: 0.0
                                                BigTitleContent(
                                                    modifier = _modifier,
                                                    title = "${weather.main?.feelsLike}°",
                                                    content = if (feelsLike > temp) {
                                                        "습도로 인해 더 따뜻하게 느껴지겠습니다."
                                                    } else {
                                                        "습도로 인해 더 선선하게 느껴지겠습니다."
                                                    }
                                                )
                                            }

                                            VerticalGridItem.HUMIDITY -> {
                                                BigTitleContent(
                                                    modifier = _modifier,
                                                    title = "${weather.main?.humidity}%"
                                                )
                                            }

                                            VerticalGridItem.VISIBILITY -> {
                                                BigTitleContent(
                                                    modifier = _modifier,
                                                    title = "${(weather.visibility ?: 0) / 1000}km"
                                                )
                                            }
                                            VerticalGridItem.PRESSURE -> {
                                                PressureContent(
                                                    modifier = _modifier,
                                                    pressure = weather.main?.pressure
                                                )
                                            }

                                            else -> {
                                                Text(modifier = _modifier, text = "testtttt2")
                                            }
                                        }
                                    }
                                }
                            }
                        }



                        List<String>(100) { "test $it" }.forEach {
                            item {
                                Text(
                                    it, modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            )
        }

    }
}