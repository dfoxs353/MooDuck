package com.example.mooduck.data.repository

import android.util.Log
import com.mooduck.domain.models.Result
import com.example.mooduck.data.remote.books.BookApi
import com.example.mooduck.data.remote.books.BooksResponse
import com.example.mooduck.data.remote.books.CertainBookResponse
import com.example.mooduck.data.remote.books.Comment
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException

class BooksRepository(
    private val bookApi: BookApi,
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun getBook(id: String): Result<CertainBookResponse> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = bookApi.getBook(id)
                    response.await()
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error loading book", e))
        }
    }

    suspend fun getBooks(limit: Int?, page: Int?, genre: String?, author: String?): Result<BooksResponse> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = bookApi.getBooks(limit,page,genre,author)
                    response.await()
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error loading books", e))
        }
    }

    suspend fun getBookComments(id: String): Result<List<Comment>> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = bookApi.getBookComments(id)
                    response.await()
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error loading {$id} book", e))
        }
    }
}