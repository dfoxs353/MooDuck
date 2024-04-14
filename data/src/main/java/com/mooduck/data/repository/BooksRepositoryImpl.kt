package com.mooduck.data.repository

import android.util.Log
import com.mooduck.data.mappers.toBooks
import com.mooduck.data.mappers.toCertainBook
import com.mooduck.data.mappers.toComment
import com.mooduck.domain.models.Result
import com.mooduck.data.remote.books.BookApi
import com.mooduck.data.remote.books.BooksResponse
import com.mooduck.data.remote.books.CertainBookResponse
import com.mooduck.data.remote.books.CommentResponse
import com.mooduck.domain.models.Book
import com.mooduck.domain.models.CertainBook
import com.mooduck.domain.models.Comment
import com.mooduck.domain.repository.BooksRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class BooksRepositoryImpl  @Inject constructor(
    private val bookApi: BookApi,
    private val ioDispatcher: CoroutineDispatcher
) : BooksRepository {
    override suspend fun getBook(id: String): Result<CertainBook> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = bookApi.getBook(id)
                    response.await()
                }.toCertainBook()
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error loading book", e))
        }
    }

    override suspend fun getBooks(
        limit: Int?,
        page: Int?,
        genre: String?,
        author: String?
    ): Result<List<Book>> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = bookApi.getBooks(limit,page,genre,author)
                    response.await()
                }.toBooks()
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error loading books", e))
        }
    }

    override suspend fun getBookComments(id: String): Result<List<Comment>> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = bookApi.getBookComments(id)
                    response.await()
                }.map {
                    it.toComment()
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error loading {$id} book", e))
        }
    }
}