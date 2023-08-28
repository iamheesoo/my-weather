package com.example.myweather.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myweather.R
import com.example.myweather.ui.theme.PrimaryTextColor

@Composable
@Preview
fun ReportItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(16.dp),
                color = Color.Black.copy(alpha = 0.3f)
            )
            .padding(10.dp)
    ) {
        Image(
            modifier = Modifier
                .padding(top = 3.dp, end = 10.dp)
                .size(24.dp),
            painter = painterResource(id = R.drawable.baseline_fmd_bad_24),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = PrimaryTextColor)
        )
        Column() {
            Text(
                text = "문제 리포트",
                color = PrimaryTextColor
            )
            Text(
                text = "현재 위치의 기상 상태를 설명하여\n일기예보를 더욱 향상시킬 수 있습니다.",
                color = Color.LightGray,
                lineHeight = 18.sp
            )
        }
    }
}