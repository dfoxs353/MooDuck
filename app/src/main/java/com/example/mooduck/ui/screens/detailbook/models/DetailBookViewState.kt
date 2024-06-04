package com.example.mooduck.ui.screens.detailbook.models

import com.mooduck.domain.models.CertainBook

enum class DetailBookSubState{
    DetailBook,Loading,Error
}
data class DetailBookViewState(
    val subState: DetailBookSubState = DetailBookSubState.Loading,
    val book: CertainBook = CertainBook(),
    val comments: List<CommentUI> = listOf(),
    val isWantToRead: Boolean = false,
    val isAddComment: Boolean = false,
    val addCommentState: AddCommentState = AddCommentState()
)

enum class AddCommentSubState{
    Nothing, Successful, Error, Loading
}
data class AddCommentState(
    val title: String = "",
    val comment: String = "",
    val addingState: AddCommentSubState = AddCommentSubState.Nothing,
)