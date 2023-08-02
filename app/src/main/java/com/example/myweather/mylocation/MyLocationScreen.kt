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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.times
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.example.myweather.R
import com.example.myweather.composable.CustomTopAppBar
import com.example.myweather.composable.HourWeather
import com.example.myweather.composable.weatherContent
import com.example.myweather.utils.buildExoPlayer
import com.example.myweather.utils.getVideoUri

@Composable
@Preview
@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
fun MyLocationScreen() {
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
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (appBar, column) = createRefs()

            CustomTopAppBar(
                modifier = Modifier
                    .constrainAs(appBar) {
                        linkTo(
                            top = parent.top,
                            bottom = column.top
                        )
                    },
                height = customTopAppBarHeight
            )

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .constrainAs(column) {
                        linkTo(
                            top = appBar.bottom,
                            bottom = parent.bottom
                        )
                        height = Dimension.preferredWrapContent
                    },
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                weatherContent(
                    titleIconId = R.drawable.round_access_alarm_24,
                    titleText = "시간별 일기예보",
                    content = {
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            List<String>(100) { "$it" }.forEachIndexed { index, s ->
                                item {
                                    HourWeather(
                                        timeText = if (index == 0) "지금" else "${s}시",
                                        degree = index.toDouble()
                                    )
                                }
                            }
                        }

                    }
                )
                item {
                    Spacer(modifier = Modifier.height(50.dp))
                }
                weatherContent(
                    titleIconId = R.drawable.round_calendar_month_24,
                    titleText = "10일간의 일기예보",
                    content = {
                        List<String>(100) { "$it" }.forEach {
                            Text(
                                it, modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    }
                )
            }
            createVerticalChain(appBar, column, chainStyle = ChainStyle.Packed)
        }
    }
}