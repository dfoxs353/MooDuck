package com.mooduck.data.remote.books

import com.mooduck.domain.models.Book
import com.mooduck.domain.models.CertainBook
import com.mooduck.domain.models.Comment
import com.mooduck.domain.models.Images


data class BookResponse(
    val _id: String,
    val authors: List<String>,
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
    val description: String,
    val genres: List<String>,
    val img: ImagesResponse,
    val pageCount: Int,
    val painters: List<String>,
    val publishedDate: String,
    val _id: String,
    val publisher: String,
    val title: String,
    val translaters: List<String>,
)




data class CommentResponse(
    val _id: String,
    val bookId: String,
    val userId: String,
    val title: String,
    val text: String,
    val rating: Int,
    val date: Long,
    val likes: List<LikeResponse>,
    val dislikes: List<LikeResponse>
)

data class LikeResponse(
    val userId: String
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



