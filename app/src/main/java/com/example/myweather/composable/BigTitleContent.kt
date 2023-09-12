package com.example.myweather.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myweather.ui.theme.Content1
import com.example.myweather.ui.theme.Content2
import com.example.myweather.ui.theme.PrimaryTextColor

@Composable
fun BigTitleContent(
    modifier: Modifier = Modifier,
    title: String? = null,
    content: String? = null
) {
    Box(
        modifier = modifier.height(100.dp)
    ) {
        if (title != null) {
            Text(
                modifier = Modifier
                    .align(if (content != null) Alignment.TopStart else Alignment.Center),
                text = title,
                fontSize = Content1.fontSize,
                fontWeight = Content1.fontWeight,
                color = PrimaryTextColor
            )
            if (content != null) {
                Text(
                    modifier = Modifier.align(Alignment.BottomStart),
                    text = content,
                    fontSize = Content2.fontSize,
                    fontWeight = Content2.fontWeight,
                    color = PrimaryTextColor
                )
            }
        } else {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 5.dp),
                text = "--",
                fontSize = Content1.fontSize,
                color = PrimaryTextColor
            )
        }
    }
}