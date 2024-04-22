package com.example.mooduck.ui.screens.home

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.mooduck.R
import com.example.mooduck.ui.models.BookUI
import com.example.mooduck.ui.screens.home.views.BookView
import com.mooduck.domain.models.Book
import com.example.mooduck.ui.theme.White

@Composable
fun ListBookScreen(
    books: LazyPagingItems<Book>,
    onBookClick: (String) -> Unit,
    onWantToReadClick: (String) -> Unit,
){


    val context = LocalContext.current
    val errorLoadText = stringResource(id = R.string.error_loading)

    LaunchedEffect(key1 = books.loadState) {
        if (books.loadState.refresh is LoadState.Error){
            Toast.makeText(
                context,
                errorLoadText,
                Toast.LENGTH_LONG
            ).show()

            Log.d("TAG", "Error: " +  (books.loadState.refresh as LoadState.Error).error.message)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if(books.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier.background(White),
                contentPadding = PaddingValues(20.dp)
            ) {
                items(count = books.itemCount){ index ->
                    if (books[index] != null){
                        BookView(
                            modifier = Modifier
                                .clickable { onBookClick(books[index]!!._id) },
                            bookUIData = BookUI(
                                id = books[index]!!._id,
                                title = books[index]!!.title,
                                description = books[index]!!.description,
                                authors = books[index]!!.authors,
                                pageCount = books[index]!!.pageCount,
                                publisher = books[index]!!.publisher,
                                imgUri = books[index]!!.img.smallFingernail,
                            ),
                            onWantToReadClick = { onWantToReadClick(books[index]!!._id) }
                        )
                        Spacer(modifier = Modifier
                            .padding(vertical = 10.dp)
                            .height(2.dp)
                            .background(Color.Gray)
                            .fillMaxWidth()
                        )
                    }
                }
                item {
                    if(books.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

