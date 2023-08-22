package com.example.myweather.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.ui.theme.PrimaryTextColor

@Composable
fun BigTitleContent(modifier: Modifier = Modifier, title: String?, content: String? = null) {
    Box(
        modifier = modifier.height(100.dp)
    ) {
        if (title != null) {
            Text(
                modifier = Modifier.align(if (content != null) Alignment.TopStart else Alignment.Center),
                text = title,
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium,
                color = PrimaryTextColor
            )
            if (content != null) {
                Text(
                    modifier = Modifier.align(Alignment.BottomStart),
                    text = content,
                    fontSize = 13.sp,
                    color = PrimaryTextColor
                )
            }
        } else {
            Text(
                modifier = Modifier.align(Alignment.Center).padding(top = 5.dp),
                text = "--",
                fontSize = 30.sp,
                color = PrimaryTextColor
            )
        }
    }
}