package com.example.mooduck.ui.screens.home.models

import com.example.mooduck.ui.models.BookUI
import com.mooduck.domain.models.BooksPage

enum class HomeSubState{
    BookList, Loading, Error
}
data class HomeViewState(
    val homeSubState: HomeSubState = HomeSubState.Loading,
    val favoriteBooks: BooksPage = BooksPage(
        bookList = emptyList(),
        page = 0,
        pageCount = 0,
    )
)