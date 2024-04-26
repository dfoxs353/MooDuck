package com.example.mooduck.ui.screens.detailbook.models

sealed class DetailBookEvent {
    object AddFavoriteBookClick: DetailBookEvent()
    object DeleteFavoriteBookClick: DetailBookEvent()

    object AddComment: DetailBookEvent()
}