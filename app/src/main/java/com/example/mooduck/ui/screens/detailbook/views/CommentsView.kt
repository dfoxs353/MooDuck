package com.example.mooduck.ui.screens.detailbook.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.mooduck.ui.screens.detailbook.models.CommentUI
import com.example.mooduck.ui.theme.MooDuckTheme
import com.mooduck.domain.models.Comment

@Composable
fun CommentView(
    modifier: Modifier = Modifier,
    comment: CommentUI,
){

    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = comment.author,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )
            Text(
                text = comment.date,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )
        }
    }

}

@Preview
@Composable
fun CommentView_Preview(){
    val comment = CommentUI(
        author = "test",
        date = "12.12.2012",
        title = "Test",
        text = "Test text",
        likes = 5,
        dislikes = 21,
    )

    MooDuckTheme {
        CommentView(
            modifier = Modifier.fillMaxWidth(),
            comment = comment
        )
    }
}