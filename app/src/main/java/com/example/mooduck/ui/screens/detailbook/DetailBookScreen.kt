package com.example.mooduck.ui.screens.detailbook

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.mooduck.ui.screens.detailbook.models.DetailBookEvent
import com.example.mooduck.ui.screens.detailbook.models.DetailBookSubState
import com.example.mooduck.ui.screens.detailbook.views.DetailBookErrorView
import com.example.mooduck.ui.screens.detailbook.views.DetailBookLoadingView
import com.example.mooduck.ui.screens.detailbook.views.DetailBookView

@Composable
fun DetailBookScreen(
    detailBookViewModel: DetailBookViewModel,
    navController: NavController,
) {

    val state = detailBookViewModel.viewState.observeAsState()

    with(state.value!!){
        when(subState){
            DetailBookSubState.DetailBook -> DetailBookView(
                detailBook = book!!,
                onAddToFavoriteClick = {detailBookViewModel.obtainEvent(DetailBookEvent.AddFavoriteBookClick)},
                onDeleteFromFavoriteClick = {detailBookViewModel.obtainEvent(DetailBookEvent.AddFavoriteBookClick)},
            )
            DetailBookSubState.Loading -> DetailBookLoadingView()
            DetailBookSubState.Error -> DetailBookErrorView()
        }

    }
}