package com.example.mooduck.ui.screens.detailbook.models

sealed class DetailBookEvent {

    object getBook: DetailBookEvent()
    object onAddFavoriteBookClick: DetailBookEvent()
    object onDeleteFavoriteBookClick: DetailBookEvent()

    object onAddCommentClick: DetailBookEvent()

    data class onLikeClick(val value: String): DetailBookEvent()
    data class onDisLikeClick(val value: String): DetailBookEvent()

    object onPublishClick : DetailBookEvent()

    data class onCommentTitleChanged(val value: String): DetailBookEvent()
    data class onCommentTextChanged(val value: String): DetailBookEvent()

    object dismissAddingComment: DetailBookEvent()
}