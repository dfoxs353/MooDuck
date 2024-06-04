package com.example.mooduck.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mooduck.R

@Composable
fun LogoWithText(
    modifier: Modifier = Modifier,
    logo: Painter,
    title: String,
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Image(
            modifier = Modifier.size(width = 35.dp, height = 30.dp),
            painter = logo,
            contentDescription = "",
        )

        Text(
            modifier = Modifier.padding(start = 5.dp),
            text = title,
            color = Color.White,
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 32.sp,
            ),
        )
    }
}