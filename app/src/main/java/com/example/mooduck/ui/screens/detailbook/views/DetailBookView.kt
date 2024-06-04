package com.example.mooduck.ui.screens.detailbook.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mooduck.R
import com.example.mooduck.ui.screens.detailbook.models.CommentUI
import com.example.mooduck.ui.theme.MooDuckTheme
import com.example.mooduck.ui.theme.TintBlack
import com.example.mooduck.ui.theme.White
import com.mooduck.domain.models.CertainBook
import com.mooduck.domain.models.Comment
import com.mooduck.domain.models.Images

@Composable
fun DetailBookView(
    detailBook: CertainBook,
    comments: List<CommentUI>,
    isWantToRead: Boolean = false,
    onAddToFavoriteClick: (String) -> Unit,
    onDeleteFromFavoriteClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onDisLikeClick: (String) -> Unit,
    onBackClick: () -> Unit,
    onAddCommentClick: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(20.dp),
    )
    {
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = onBackClick
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back")
                }
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {

                    Image(
                        modifier = Modifier.size(width = 35.dp, height = 30.dp),
                        painter = painterResource(id = R.drawable.duck_picture),
                        contentDescription = "",
                    )

                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = stringResource(id = R.string.app_name),
                        color = Color.Black,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 32.sp,
                        ),
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
            )

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
                    .padding(10.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(detailBook.img.largeFingernail)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.FillHeight,
                contentDescription = "Cover",
                placeholder = painterResource(id = R.drawable.duck_picture),
                error = painterResource(id = R.drawable.shoot_duck),
            )

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = detailBook.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = detailBook.description,
                fontSize = 16.sp
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                shape = RoundedCornerShape(size = 2.dp),
                border = BorderStroke(1.dp, TintBlack),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isWantToRead) TintBlack else White
                ),
                onClick = {
                    if (isWantToRead)
                        onDeleteFromFavoriteClick(detailBook._id)
                    else
                        onAddToFavoriteClick(detailBook._id)
                },
                contentPadding = PaddingValues(vertical = 14.dp, horizontal = 33.dp)
            ) {
                Text(
                    text = stringResource(
                        id = if (isWantToRead)
                            R.string.want_to_read
                        else R.string.not_want_to_read
                    ),
                    style = TextStyle(
                        color = if (isWantToRead) White else TintBlack,
                        fontSize = 17.sp,
                        fontWeight = FontWeight(600),
                    )
                )
            }
            Text(
                modifier = Modifier.padding(vertical = 20.dp),
                text = stringResource(id = R.string.about_book),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            AboutBookItem(
                title = stringResource(id = R.string.genre),
                data = detailBook.genres.joinToString(),
            )
            AboutBookItem(
                title = stringResource(id = R.string.publishing_house),
                data = detailBook.publisher,
            )
            AboutBookItem(
                title = stringResource(id = R.string.series),
                data = detailBook.bookSeries,
            )
            AboutBookItem(
                title = stringResource(id = R.string.binding),
                data = detailBook.bookBinding,
            )
            AboutBookItem(
                title = stringResource(id = R.string.artist),
                data = detailBook.painters.joinToString(),
            )
            AboutBookItem(
                title = stringResource(id = R.string.translator),
                data = detailBook.translaters.joinToString(),
            )
            AboutBookItem(
                title = stringResource(id = R.string.year_publishing),
                data = detailBook.publishedDate,
            )
            AboutBookItem(
                title = stringResource(id = R.string.count_list),
                data = detailBook.pageCount.toString(),
            )



            Spacer(
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(top = 10.dp, bottom = 20.dp),
                    text = stringResource(id = R.string.comments),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )

                Button(
                    modifier = Modifier,
                    shape = RoundedCornerShape(size = 2.dp),
                    border = BorderStroke(1.dp, TintBlack),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White
                    ),
                    contentPadding = PaddingValues(10.dp),
                    onClick = onAddCommentClick,
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.add_comment
                        ),
                        style = TextStyle(
                            color = TintBlack,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                        )
                    )
                }
            }


        }

        items(count = comments.size) {
            CommentView(
                modifier = Modifier.padding(bottom = 10.dp),
                comment = comments[it],
                onThumbUpClick = {onLikeClick(comments[it].id)},
                onThumbDownClick = {onDisLikeClick(comments[it].id)},
            )
        }
    }
}

@Composable
fun AboutBookItem(
    modifier: Modifier = Modifier,
    title: String,
    data: String,
) {
    Row(
        modifier = modifier.padding(bottom = 10.dp)
    ) {
        Text(
            modifier = Modifier
                .weight(1f),
            text = title,
            fontWeight = FontWeight.Light,
            fontSize = 20.sp
        )
        Text(
            modifier = Modifier
                .weight(1f),
            text = data,
            fontSize = 20.sp
        )
    }
}

@Composable
fun DetailBookLoadingView(
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    )
    {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = onBackClick
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back")
                }
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {

                    Image(
                        modifier = Modifier.size(width = 35.dp, height = 30.dp),
                        painter = painterResource(id = R.drawable.duck_picture),
                        contentDescription = "",
                    )

                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = stringResource(id = R.string.app_name),
                        color = Color.Black,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 32.sp,
                        ),
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
            )
        }

        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Spacer(
            modifier = Modifier
                .padding(vertical = 15.dp)
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.Gray)
        )
    }
}


@Composable
fun DetailBookErrorView(
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    )
    {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.CenterStart),
                    onClick = onBackClick
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back")
                }
                Row(
                    modifier = Modifier.align(Alignment.Center),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {

                    Image(
                        modifier = Modifier.size(width = 35.dp, height = 30.dp),
                        painter = painterResource(id = R.drawable.duck_picture),
                        contentDescription = "",
                    )

                    Text(
                        modifier = Modifier.padding(start = 5.dp),
                        text = stringResource(id = R.string.app_name),
                        color = Color.Black,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontSize = 32.sp,
                        ),
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .padding(vertical = 15.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp),
                painter = painterResource(id = R.drawable.duck_picture),
                contentDescription = "logo",
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = stringResource(id = R.string.error_loading),
                fontSize = 21.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier
                .padding(vertical = 15.dp)
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.Gray)
        )
    }
}

@Preview
@Composable
fun DetailBookLoading_Preview() {
    MooDuckTheme {
        DetailBookLoadingView(
            onBackClick = {}
        )
    }
}

@Preview
@Composable
fun DetailBookError_Preview() {
    MooDuckTheme {
        DetailBookErrorView(
            onBackClick = {},
        )
    }
}

@Preview
@Composable
fun DetailBookView_Preview() {
    val detailBook = CertainBook(
        authors = listOf("Test1", "Test2"),
        title = "Test Test Test Test Test Test Test Test Test",
        description = "Test",
        pageCount = 10,
        publisher = "Test",
        publishedDate = "12.01.2021",
        painters = listOf("Test1", "Test2"),
        genres = listOf("Test1", "Test2", "Test1", "Test2"),
        translaters = listOf("Test1", "Test2"),
        img = Images(
            "test", "test", "test"
        ),
        _id = "test",
        bookBinding = "test",
        bookSeries = "test",
    )

    val comment = CommentUI(
        author = "test",
        date = "12.12.2012",
        title = "Test",
        text = "Test text \nTest text \nTest text\nTest text\nTest text\nTest text",
        likes = 5,
        dislikes = 21,
        id = "",
    )

    MooDuckTheme {
        DetailBookView(
            isWantToRead = true,
            detailBook = detailBook,
            comments = listOf(comment, comment, comment),
            onDeleteFromFavoriteClick = {},
            onAddToFavoriteClick = {},
            onLikeClick = {},
            onDisLikeClick = {},
            onBackClick = {},
            onAddCommentClick = {},
        )
    }
}