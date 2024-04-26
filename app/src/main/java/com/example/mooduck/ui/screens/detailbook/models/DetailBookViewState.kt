package com.example.mooduck.ui.screens.detailbook.models

import com.mooduck.domain.models.CertainBook

enum class DetailBookSubState{
    DetailBook,Loading,Error
}
data class DetailBookViewState(
    val subState: DetailBookSubState = DetailBookSubState.Loading,
    val book: CertainBook? = null,
    val isWantToRead: Boolean = false,
)