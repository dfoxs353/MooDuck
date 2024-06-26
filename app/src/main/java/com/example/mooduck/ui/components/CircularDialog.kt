package com.example.mooduck.ui.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mooduck.ui.theme.Cian

@Composable
fun CircularDialog(titleText: String){
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = titleText,
                textAlign = TextAlign.Center,
            )
        },
        text = {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(60.dp),
                    color = Cian,
                )
            }
        },
    )
}

@Preview
@Composable
fun CircularDialog_Preview(){
    CircularDialog(
        titleText = "TitleText"
    )
}