package com.mooduck.data.remote.books

data class BookRequest(
    val id: String
)

data class BooksRequest(
    val limit: Int?,
    val page: Int?,
    val genre: String?,
    val author: String?
)

data class CommentRequest(
    val title: String,
    val text: String,
    val rating: Int,
)