package com.example.mooduck.ui.screens.detailbook.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mooduck.R
import com.example.mooduck.ui.screens.detailbook.models.CommentUI
import com.example.mooduck.ui.screens.detailbook.models.LikeState
import com.example.mooduck.ui.theme.Cian
import com.example.mooduck.ui.theme.MooDuckTheme
import com.example.mooduck.ui.theme.colorsComment
import kotlin.random.Random

@Composable
fun CommentView(
    modifier: Modifier = Modifier,
    comment: CommentUI,
    onThumbUpClick: () -> Unit,
    onThumbDownClick: () -> Unit,
) {

    val randomColor by remember {
        mutableStateOf(colorsComment[Random.nextInt(colorsComment.size)])
    }

    var isFullTextComment by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = randomColor
        ),
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = comment.title,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
            )

            if (isFullTextComment) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = comment.text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                )
            } else {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = comment.text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                TextButton(
                    contentPadding = PaddingValues(0.dp),
                    onClick = { isFullTextComment = !isFullTextComment }
                ) {
                    Text(
                        text = if (!isFullTextComment) {
                            stringResource(id = R.string.show_all)
                        } else {
                            stringResource(id = R.string.hide_text)
                        },
                        color = Cian
                    )
                }

                Row {
                    IconButton(onClick = onThumbUpClick) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 5.dp),
                                text = comment.likes.toString()
                            )
                            Icon(
                                imageVector = when (comment.likeState) {
                                    LikeState.Like -> Icons.Filled.ThumbUp
                                    LikeState.DisLike -> Icons.Outlined.ThumbUp
                                    LikeState.None -> Icons.Outlined.ThumbUp
                                },
                                contentDescription = ""
                            )
                        }
                    }

                    IconButton(onClick = onThumbDownClick) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 5.dp),
                                text = comment.dislikes.toString()
                            )
                            Icon(
                                modifier = Modifier.graphicsLayer(
                                    rotationX = 180f
                                ),
                                imageVector = when (comment.likeState) {
                                    LikeState.DisLike -> Icons.Filled.ThumbUp
                                    LikeState.Like -> Icons.Outlined.ThumbUp
                                    LikeState.None -> Icons.Outlined.ThumbUp
                                },
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun CommentView_Preview() {
    val comment = CommentUI(
        author = "test",
        date = "12.12.2012",
        title = "Test",
        text = "Test text \nTest text \nTest text\nTest text\nTest text\nTest text",
        likes = 5,
        dislikes = 21,
        likeState = LikeState.DisLike,
        id = "",
    )

    MooDuckTheme {
        CommentView(
            comment = comment,
            onThumbUpClick = {},
            onThumbDownClick = {},
        )
    }
}