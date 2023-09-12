package com.example.myweather.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myweather.ui.theme.SubTitle2

fun LazyListScope.weatherContent(
    @DrawableRes titleIconId: Int,
    titleText: String,
    content: @Composable () -> Unit,
    key: String,
    contentType: Any
) {
    item(key = "weatherContent$key", contentType = "weatherContent$contentType") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.Black.copy(alpha = 0.3f)
                )
                .padding(vertical = 10.dp),
        ) {
            Row(
                modifier = Modifier.padding(start = 14.dp, end = 14.dp, bottom = 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    painter = painterResource(id = titleIconId),
                    contentDescription = "titleIcon",
                    modifier = Modifier.size(10.dp),
                    tint = Color.White
                )
                Text(
                    modifier = Modifier
                        .padding(start = 5.dp),
                    text = titleText,
                    color = Color.White,
                    fontSize = SubTitle2.fontSize
                )
            }
            content.invoke()
        }
    }
}

