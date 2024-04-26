package com.mooduck.data.mappers

import com.mooduck.data.local.models.BookEntity
import com.mooduck.data.remote.books.BookResponse
import com.mooduck.data.remote.books.BooksResponse
import com.mooduck.data.remote.books.CertainBookResponse
import com.mooduck.data.remote.books.CommentResponse
import com.mooduck.data.remote.books.ImagesResponse
import com.mooduck.domain.models.Book
import com.mooduck.domain.models.BooksPage
import com.mooduck.domain.models.CertainBook
import com.mooduck.domain.models.Comment
import com.mooduck.domain.models.Images

internal fun Book.toBookEntity(): BookEntity{
    return BookEntity(
        id = _id.hashCode(),
        _id = _id,
        title = title,
        description = description,
        authors = authors.joinToString(separator = ","),
        publisher = publisher,
        pageCount = pageCount,
        imgUrls =
            "${img.largeFingernail}," +
            "${img.mediumFingernail}," +
                    img.smallFingernail
    )
}

fun BookEntity.toBook(): Book{
    val images = imgUrls.split(",")

    return Book(
        _id = _id,
        title = title,
        description = description,
        authors = authors.split(","),
        publisher = publisher,
        pageCount = pageCount,
        img = Images(
            largeFingernail =images[0],
            mediumFingernail =images[1],
            smallFingernail =images[2],
        ),
    )
}

internal fun BooksResponse.toBooksPage() : BooksPage{
    return BooksPage(
        bookList = this.books.map {
            it.toBook()
        },
        page = page,
        pageCount = totalPages,
    )
}
internal fun BookResponse.toBook() = Book(
    _id,
    authors,
    title,
    img,
    description,
    pageCount,
    publisher
)

internal fun BookResponse.toBookEntity() = BookEntity(
    id = _id.hashCode(),
    _id = _id,
    authors = authors.joinToString(separator = ","),
    title = title,
    imgUrls = "${img.largeFingernail}," +
            "${img.mediumFingernail}," +
            img.smallFingernail,
    description = description,
    pageCount = pageCount,
    publisher = publisher,
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