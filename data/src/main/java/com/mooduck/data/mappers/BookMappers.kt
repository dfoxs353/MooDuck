package com.mooduck.data.mappers

import com.mooduck.data.remote.books.BookResponse
import com.mooduck.data.remote.books.BooksResponse
import com.mooduck.data.remote.books.CertainBookResponse
import com.mooduck.data.remote.books.CommentResponse
import com.mooduck.data.remote.books.ImagesResponse
import com.mooduck.domain.models.Book
import com.mooduck.domain.models.CertainBook
import com.mooduck.domain.models.Comment
import com.mooduck.domain.models.Images

internal fun BooksResponse.toBooks() : List<Book>{
    return this.books.map {
        it.toBook()
    }
}
internal fun BookResponse.toBook() = Book(
    _id,
    authors,
    geners,
    title,
    img,
    description,
    pageCount,
    publisher
)

internal fun CertainBookResponse.toCertainBook() = CertainBook(
    authors,
    bookBinding,
    bookSeries,
    commentResponses.map {
        it.toComment()
    },
    description,
    genres,
    img,
    pageCount,
    painters,
    publishedDate,
    _id,
    publisher,
    title,
    translaters
)


internal fun CommentResponse.toComment() = Comment(
    bookId,
    date,
    dislikes,
    likes,
    rating,
    text,
    title,
    userId,
    _id
)
internal fun ImagesResponse.toImages() = Images(
    largeFingernail,
    mediumFingernail,
    smallFingernail,
)