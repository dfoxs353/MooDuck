package com.example.mooduck.ui.screens.home.models

import com.example.mooduck.ui.models.Book

enum class HomeSubState{
    BookList, Loading, Error
}
data class HomeViewState(
    val homeSubState: HomeSubState = HomeSubState.Loading,
    val bookList: List<Book> = listOf()
)