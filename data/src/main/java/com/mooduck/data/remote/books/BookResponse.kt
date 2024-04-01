package com.mooduck.data.remote.books

import com.mooduck.domain.models.Book
import com.mooduck.domain.models.CertainBook
import com.mooduck.domain.models.Comment
import com.mooduck.domain.models.Images


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
    val commentResponses: List<CommentResponse>,
    val description: String,
    val genres: List<String>,
    val img: Images,
    val pageCount: Int,
    val painters: List<String>,
    val publishedDate: String,
    val _id: String,
    val publisher: String,
    val title: String,
    val translaters: List<String>,
)




data class CommentResponse(
    val bookId: String,
    val date: Int,
    val dislikes: Int,
    val likes: Int,
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
    val commentResponses: List<CommentResponse>,
    val lines: Boolean,
)

data class ImagesResponse(
    val largeFingernail: String,
    val mediumFingernail: String,
    val smallFingernail: String,
)



