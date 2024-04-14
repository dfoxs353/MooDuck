package com.example.mooduck.ui.screens.home.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.mooduck.R
import com.example.mooduck.ui.models.Book
import com.example.mooduck.ui.theme.Cian
import com.example.mooduck.ui.theme.TintBlack
import com.example.mooduck.ui.theme.White

@Composable
fun BookView(
    modifier: Modifier = Modifier,
    bookData: Book,
    onWantToReadClick: (String) -> Unit,
) {
    val authors = bookData.authors.joinToString()

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(2.dp ),
        colors = CardDefaults.cardColors(
            containerColor = White,
        ),
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                modifier = Modifier.width(160.dp).padding(10.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(bookData.imgUri)
                    .crossfade(true)
                    .build(),
                contentDescription = "Cover",
                placeholder = painterResource(id = R.drawable.duck_picture),
                error = painterResource(id = R.drawable.shoot_duck),
                )
            Column {
                Text(
                    text = bookData.title,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = authors,
                    fontWeight = FontWeight(200),
                )
                Text(
                    text = bookData.description,
                    maxLines = 6,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = bookData.publisher + ", " + bookData.pageCount,
                    fontWeight = FontWeight(200),
                )
            }
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            shape = RoundedCornerShape(size = 2.dp),
            border = BorderStroke(1.dp, TintBlack),
            colors = ButtonDefaults.buttonColors(
                containerColor = if(bookData.isWantToRead) TintBlack else White
            ),
            onClick = { onWantToReadClick(bookData.imgUri) },
            contentPadding = PaddingValues(vertical = 14.dp, horizontal = 33.dp)
        ) {
            Text(
                text = stringResource(
                    id = if(bookData.isWantToRead)
                            R.string.want_to_read
                        else R.string.not_want_to_read
                ),
                style = TextStyle(
                    color = if(bookData.isWantToRead) White else TintBlack,
                    fontSize = 17.sp,
                    fontWeight = FontWeight(600),
                )
            )
        }
    }
}

@Preview
@Composable
fun BookView_Preview() {
    val previewBook = Book(
        id ="",
        title = "Красная ягода. Черная земля. Сборник стихов",
        description = "Анна Долгарева — поэтесса, военкор и журналист, победитель множества" +
                " литературных конкурсов и самый молодой лауреат Григорьевской премии. " +
                "Поэзия Анны Долгаревой поднимает вечные вопросы бытия...",
        authors = listOf("Анна Долгарева","Анна Долгарева"),
        pageCount = 224,
        publisher = "ACT",
        isWantToRead = false,
    )

    BookView(
        bookData = previewBook,
        onWantToReadClick = {},
    )
}