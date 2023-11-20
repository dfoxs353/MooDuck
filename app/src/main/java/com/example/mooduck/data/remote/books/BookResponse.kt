package com.example.mooduck.data.remote.books

import com.example.mooduck.data.remote.auth.AuthResponse


data class CertainBookResult(
    val success: CertainBookResponse? = null,
    val error: Int? = null,
)

data class BooksResult(
    val success: BooksResponse? = null,
    val error: Int? = null,
)

data class BookResponse(
    val _id: String,
    val authors: List<String>,
    val geners: List<String>,
    val title: String,
    val img: Images,
    val description: String,
    val pageCount: Int,
    val publisher: String,
)

data class CertainBookResponse(
    val authors: List<String>,
    val bookBinding: String,
    val bookSeries: String,
    val comments: List<Comment>,
    val description: String,
    val genres: List<String>,
    val img: Images,
    val pageCount: Int,
    val painters: List<String>,
    val publishedDate: String,
    val _id: String,
    val publisher:String,
    val title: String,
    val translaters: List<String>,
)


data class Comment(
    val bookId: String,
    val date: Int,
    val dislikes: ArrayList<Int>,
    val likes: ArrayList<Int>,
    val rating: Int,
    val text: String,
    val title: String,
    val userId: String,
    val _id: String,
)


data class BooksResponse(
    val books: List<BookResponse>,
    val isLoading: Boolean,
    val error: String,
    val totalPages: Int,
    val hasNextPage: Boolean,
    val hasPrevPage: Boolean,
    val page: Int,
    val comments: List<Comment>,
    val lines: Boolean,
)
data class Images(
    val largeFingernail: String,
    val mediumFingernail: String,
    val smallFingernail: String,
)
