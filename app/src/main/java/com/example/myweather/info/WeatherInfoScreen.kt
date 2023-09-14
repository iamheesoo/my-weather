package com.example.myweather.info

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
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
import com.example.myweather.composable.ReportItem
import com.example.myweather.composable.TodayWeather
import com.example.myweather.composable.TransparentColumn
import com.example.myweather.composable.VerticalGrid
import com.example.myweather.composable.VerticalGridContent
import com.example.myweather.composable.WindContent
import com.example.myweather.composable.weatherContent
import com.example.myweather.data.LatAndLon
import com.example.myweather.ui.theme.Content3
import com.example.myweather.ui.theme.PrimaryTextColor
import com.example.myweather.utils.buildExoPlayer
import com.example.myweather.utils.dtTxtToHour
import com.example.myweather.utils.getAirQualityInfo
import com.example.myweather.utils.getVideoUri
import com.example.myweather.utils.getWeatherIconDrawable
import kotlin.math.roundToInt

@Composable
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun WeatherInfoScreen(
    location: LatAndLon,
    viewModel: WeatherInfoViewModel
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val weather = state.value.hashMap[location]?.weather
    val weatherHourlyList = state.value.hashMap[location]?.weatherHourlyList
    val airPollution = state.value.hashMap[location]?.airPollution

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

    LaunchedEffect(listFirstVisibleItemIndex) {
        customTopAppBarHeight = max(400.dp - (listFirstVisibleItemIndex.times(100.dp)), 100.dp)
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
        if (!viewModel.isMapContainsLocation(location)) {
            viewModel.sendEvent(WeatherInfoContract.Event.RequestWeatherInfo)
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        } else if (weather != null) {
            TransparentColumn(
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
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        if (weatherHourlyList?.isNotEmpty() == true) {
                            weatherContent(
                                key = WeatherInfoType.HOUR_WEATHER.name,
                                contentType = WeatherInfoType.HOUR_WEATHER,
                                titleIconId = WeatherInfoType.HOUR_WEATHER.icon,
                                titleText = WeatherInfoType.HOUR_WEATHER.title,
                                content = {
                                    LazyRow(
                                        contentPadding = PaddingValues(horizontal = 14.dp),
                                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                                    ) {
                                        items(
                                            count = weatherHourlyList.size,
                                            key = { index -> "${WeatherInfoType.HOUR_WEATHER.name}$index" },
                                            contentType = { WeatherInfoType.HOUR_WEATHER }
                                        ) { index ->
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
                            )
                        }

                        weatherContent(
                            key = WeatherInfoType.TEN_DAY_WEATHER.name,
                            contentType = WeatherInfoType.TEN_DAY_WEATHER,
                            titleIconId = WeatherInfoType.TEN_DAY_WEATHER.icon,
                            titleText = WeatherInfoType.TEN_DAY_WEATHER.title,
                            content = {
                                TodayWeather(
                                    icon = context.getWeatherIconDrawable(weather.weatherList?.firstOrNull()?.icon),
                                    tempMin = weather.main?.tempMin?.roundToInt(),
                                    tempMax = weather.main?.tempMax?.roundToInt()
                                )
                            }
                        )

                        if (airPollution?.list?.firstOrNull() != null) {
                            val item = airPollution.list.first()
                            weatherContent(
                                key = WeatherInfoType.AIR_POLLUTION.name,
                                contentType = WeatherInfoType.AIR_POLLUTION,
                                titleIconId = WeatherInfoType.AIR_POLLUTION.icon,
                                titleText = WeatherInfoType.AIR_POLLUTION.title,
                                content = {
                                    AirPollutionContent(
                                        aqi = item.main?.aqi ?: 1,
                                        content = getAirQualityInfo(item.main?.aqi)
                                    )
                                }
                            )
                        }

                        item(
                            key = WeatherInfoType.VERTICAL_GRID.name,
                            contentType = WeatherInfoType.VERTICAL_GRID.name
                        ) {
                            VerticalGrid(
                                columns = 2
                            ) {
                                InfoVerticalGridItem.values().forEach { item ->
                                    VerticalGridContent(
                                        titleIconId = item.icon,
                                        titleText = item.title,
                                        paddingValues = PaddingValues(4.dp)
                                    ) { _modifier ->
                                        when (item) {
                                            InfoVerticalGridItem.WIND -> {
                                                WindContent(
                                                    modifier = _modifier,
                                                    speed = weather.wind?.speed ?: 0.0,
                                                    deg = weather.wind?.deg
                                                )
                                            }

                                            InfoVerticalGridItem.RAINY -> {
                                                BigTitleContent(
                                                    modifier = _modifier,
                                                    title = if (weather.rain?.hour1 != null) {
                                                        "${weather.rain.hour1}mm"
                                                    } else {
                                                        null
                                                    }
                                                )
                                            }

                                            InfoVerticalGridItem.TEMPERATURE -> {
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

                                            InfoVerticalGridItem.HUMIDITY -> {
                                                BigTitleContent(
                                                    modifier = _modifier,
                                                    title = "${weather.main?.humidity}%"
                                                )
                                            }

                                            InfoVerticalGridItem.VISIBILITY -> {
                                                BigTitleContent(
                                                    modifier = _modifier,
                                                    title = "${(weather.visibility ?: 0) / 1000}km"
                                                )
                                            }

                                            InfoVerticalGridItem.PRESSURE -> {
                                                PressureContent(
                                                    modifier = _modifier,
                                                    pressure = weather.main?.pressure
                                                )
                                            }

                                            else -> {
                                                BigTitleContent(
                                                    modifier = _modifier
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        item(key = WeatherInfoType.REPORT.name) {
                            ReportItem(title = WeatherInfoType.REPORT.title)
                        }

                        item {
                            Divider(
                                thickness = 1.dp,
                                color = Color.Gray
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterStart),
                                    text = "지도에서 열기",
                                    color = Color.Gray,
                                    fontSize = Content3.fontSize
                                )
                                Image(
                                    modifier = Modifier.align(Alignment.CenterEnd),
                                    painter = painterResource(id = R.drawable.round_arrow_outward_24),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color.Gray)
                                )
                            }
                            Divider(
                                thickness = 1.dp,
                                color = Color.Gray
                            )
                        }

                        item {
                            Text(
                                text = "${weather.name} 날씨",
                                color = PrimaryTextColor,
                                fontSize = Content3.fontSize
                            )
                        }

                        item {
                            Text(
                                modifier = Modifier
                                    .padding(top = 6.dp),
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                                        append("날씨 데이터")
                                    }
                                    append(" 및 ")
                                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                                        append("지도 데이터")
                                    }
                                    append("에 관하여 더 알아보기")
                                },
                                color = Color.Gray,
                                fontSize = Content3.fontSize
                            )
                        }


                        item {
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }
                }
            )
        } else {
            Text("데이터 없음")
        }

    }
}

private enum class WeatherInfoType(val title: String, @DrawableRes val icon: Int) {
    HOUR_WEATHER("시간별 일기예보", R.drawable.round_access_alarm_24),
    TEN_DAY_WEATHER("10일간의 일기예보", R.drawable.round_calendar_month_24),
    AIR_POLLUTION("대기질", R.drawable.baseline_blur_on_24),
    VERTICAL_GRID("vertical_grid", 0),
    REPORT("문제 리포트", 0)
}