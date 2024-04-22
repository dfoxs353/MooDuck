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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ListBookScreen(
    books: LazyPagingItems<Book>,
    onBookClick: (String) -> Unit,
    onWantToReadClick: (String) -> Unit,
){


    val context = LocalContext.current
    val errorLoadText = stringResource(id = R.string.error_loading)

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        books.refresh()
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    LaunchedEffect(key1 = books.loadState) {
        if (books.loadState.refresh is LoadState.Error){
            Toast.makeText(
                context,
                errorLoadText,
                Toast.LENGTH_LONG
            ).show()

            Log.d("TAG", "Error: " +  (books.loadState.refresh as LoadState.Error).error.message)
        }

        if (books.loadState.refresh is LoadState.Loading){
            refreshing = true
        }

        if (books.loadState.refresh is LoadState.NotLoading){
            refreshing = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        PullRefreshIndicator(refreshing = refreshing, state = state, Modifier.align(Alignment.TopCenter))

        if (!refreshing){
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .pullRefresh(state)
                    .background(White),
                contentPadding = PaddingValues(20.dp),
            ) {
                items(count = books.itemCount) { index ->
                    if (books[index] != null) {
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
                                imgUri = books[index]!!.img.mediumFingernail,
                            ),
                            onWantToReadClick = { onWantToReadClick(books[index]!!._id) }
                        )
                        Spacer(
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .height(2.dp)
                                .background(Color.Gray)
                                .fillMaxWidth()
                        )
                    }
                }
                item {
                    if (books.loadState.append is LoadState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

