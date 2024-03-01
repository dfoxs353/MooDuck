package com.mooduck.domain.repository

import com.mooduck.domain.models.Book
import com.mooduck.domain.models.Comment

interface BooksRepository {

    suspend fun getBook(id: String): Result<Book>

    suspend fun getBooks(limit: Int?, page: Int?, genre: String?, author: String): Result<List<Book>>

    suspend fun getBookComments(id: String): Result<List<Comment>>
}