package com.example.myweather.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myweather.ui.theme.SubTitle3

@Composable
fun VerticalGridContent(
    @DrawableRes titleIconId: Int,
    titleText: String,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    content: @Composable (Modifier) -> Unit
) {
    Column(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp
                    ),
                    color = Color.Black.copy(alpha = 0.3f)
                )
                .padding(vertical = 8.dp, horizontal = 14.dp),
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
                fontSize = SubTitle3.fontSize
            )
        }



        content.invoke(
            Modifier
                .fillMaxWidth()
                .background(color = Color.Black.copy(alpha = 0.3f))
                .padding(horizontal = 14.dp)
        )



        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    shape = RoundedCornerShape(
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    ),
                    color = Color.Black.copy(alpha = 0.3f)
                )
                .padding(vertical = 8.dp, horizontal = 14.dp),
        )
    }
}


