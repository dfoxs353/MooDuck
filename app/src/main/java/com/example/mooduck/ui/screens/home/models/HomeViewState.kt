package com.example.mooduck.ui.screens.home.models

import com.example.mooduck.ui.models.BookUI

enum class HomeSubState{
    BookList, Loading, Error
}
data class HomeViewState(
    val homeSubState: HomeSubState = HomeSubState.Loading,
    val bookUIList: List<BookUI> = listOf()
)