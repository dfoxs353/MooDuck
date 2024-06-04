package com.mooduck.domain.repository

import com.mooduck.domain.models.BooksPage
import com.mooduck.domain.models.CertainBook
import com.mooduck.domain.models.Comment
import com.mooduck.domain.models.Result

interface BooksRepository {

    suspend fun getBook(id: String): Result<CertainBook>

    suspend fun getBooks(text: String? = null,limit: Int?, page: Int?, genre: String?, author: String?): Result<BooksPage>

    suspend fun getBookComments(id: String): Result<List<Comment>>
    suspend fun addCommentToBook(bookId: String,title: String, text: String, rating: Int): Result<Boolean>

    suspend fun addLikeToComment(commentId: String): Result<Boolean>
    suspend fun addDisLikeToComment(commentId: String): Result<Boolean>

    suspend fun deleteLikeToComment(commentId: String): Result<Boolean>
    suspend fun deleteDisLikeToComment(commentId: String): Result<Boolean>
}