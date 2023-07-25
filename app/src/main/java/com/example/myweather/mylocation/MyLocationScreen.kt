package com.example.myweather.mylocation

import android.util.Log.e
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myweather.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MyLocationScreen() {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val scrollState = rememberLazyListState()
//    CollapsingEffectScreen()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.FillWidth,
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = null,
                        )
                        Text("서울", modifier = Modifier.align(Alignment.Center))
                    }

                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Gray,
                ),
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Yellow)
                .padding(innerPadding)
                .nestedScroll(scrollBehavior.nestedScrollConnection)
        ) {
            items(items = List<String>(1000) { "$it" }) {
                Text(it, modifier = Modifier.fillMaxWidth())
            }
        }

    }

}

@Composable
fun CollapsingEffectScreen() {
    val items = (1..100).map { "Item $it" }
    val lazyListState = rememberLazyListState()
    var scrolledY = 0f
    var previousOffset = 0
    LazyColumn(
        Modifier.fillMaxSize(),
        lazyListState,
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .graphicsLayer {
                        scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                        translationY = scrolledY * 0.5f
                        previousOffset = lazyListState.firstVisibleItemScrollOffset
                    }
                    .height(240.dp)
                    .fillMaxWidth()
            )
        }
        items(items) {
            Text(
                text = it,
                Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}