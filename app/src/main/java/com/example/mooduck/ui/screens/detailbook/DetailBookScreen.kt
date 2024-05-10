package com.example.mooduck.ui.screens.detailbook

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.example.mooduck.ui.screens.detailbook.models.DetailBookEvent
import com.example.mooduck.ui.screens.detailbook.models.DetailBookSubState
import com.example.mooduck.ui.screens.detailbook.views.AddCommentModal
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
        AnimatedVisibility(visible = isAddComment) {
            AddCommentModal(
                onTitleChange = {detailBookViewModel.obtainEvent(DetailBookEvent.onCommentTitleChanged(it))},
                onCommentChange = {detailBookViewModel.obtainEvent(DetailBookEvent.onCommentTextChanged(it))},
                onPublishClick = {detailBookViewModel.obtainEvent(DetailBookEvent.onPublishClick)},
                onDismissRequest = {detailBookViewModel.obtainEvent(DetailBookEvent.dismissAddingComment)},
                state = addCommentState.addingState,
            )
        }

        when(subState){
            DetailBookSubState.DetailBook -> DetailBookView(
                isWantToRead = isWantToRead,
                detailBook = book,
                comments = comments,
                onAddToFavoriteClick = {detailBookViewModel.obtainEvent(DetailBookEvent.onAddFavoriteBookClick)},
                onDeleteFromFavoriteClick = {detailBookViewModel.obtainEvent(DetailBookEvent.onDeleteFavoriteBookClick)},
                onLikeClick = {detailBookViewModel.obtainEvent(DetailBookEvent.onLikeClick(it))},
                onDisLikeClick = {detailBookViewModel.obtainEvent(DetailBookEvent.onDisLikeClick(it))},
                onBackClick = {navController.navigateUp()},
                onAddCommentClick = {detailBookViewModel.obtainEvent(DetailBookEvent.onAddCommentClick)},
            )
            DetailBookSubState.Loading -> DetailBookLoadingView(
                onBackClick = {navController.navigateUp()}
            )
            DetailBookSubState.Error -> DetailBookErrorView(
                onBackClick = {navController.navigateUp()}
            )
        }

    }
}