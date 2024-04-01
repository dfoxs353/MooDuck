package com.mooduck.domain.repository

import com.mooduck.domain.models.Book
import com.mooduck.domain.models.CertainBook
import com.mooduck.domain.models.Comment
import com.mooduck.domain.models.Result

interface BooksRepository {

    suspend fun getBook(id: String): Result<CertainBook>

    suspend fun getBooks(limit: Int?, page: Int?, genre: String?, author: String): Result<List<Book>>

    suspend fun getBookComments(id: String): Result<List<Comment>>
}