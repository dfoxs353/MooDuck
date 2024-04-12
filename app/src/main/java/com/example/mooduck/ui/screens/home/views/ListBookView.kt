package com.example.mooduck.ui.screens.home.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mooduck.ui.models.Book
import com.example.mooduck.ui.theme.White

@Composable
fun ListBookView(
    bookList: List<Book>,
    onBookClick: (String) -> Unit,
    onWantToReadClick: (String) -> Unit,
){
    Column(
        modifier = Modifier,
    ) {
        LazyColumn(
            modifier = Modifier.background(White),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(bookList){book->
                BookView(
                    modifier = Modifier
                        .clickable { onBookClick(book.id) },
                    bookData = book,
                    onWantToReadClick = { onWantToReadClick(book.id) }
                )
                Spacer(modifier = Modifier
                    .padding(vertical = 10.dp)
                    .height(2.dp)
                    .background(Color.Gray)
                    .fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun ListBook_Preview(){
    val listBook = mutableListOf<Book>().apply {
        repeat(10){
            this.add(
                Book(
                    id = "",
                    title = "Красная ягода. Черная земля. Сборник стихов",
                    description = "Анна Долгарева — поэтесса, военкор и журналист, победитель множества" +
                            " литературных конкурсов и самый молодой лауреат Григорьевской премии. " +
                            "Поэзия Анны Долгаревой поднимает вечные вопросы бытия...",
                    authors = listOf("Анна Долгарева","Анна Долгарева"),
                    pageCount = 224,
                    publisher = "ACT",
                    isWantToRead = false,
                )
            )
        }
    }

    ListBookView(
        bookList = listBook,
        onWantToReadClick = {},
        onBookClick = {},
    )
}