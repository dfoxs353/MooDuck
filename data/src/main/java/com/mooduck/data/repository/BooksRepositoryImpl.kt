package com.mooduck.data.repository

import android.util.Log
import com.mooduck.data.mappers.toBooksPage
import com.mooduck.data.mappers.toCertainBook
import com.mooduck.data.mappers.toComment
import com.mooduck.domain.models.Result
import com.mooduck.data.remote.books.BookApi
import com.mooduck.data.remote.books.BooksResponse
import com.mooduck.data.remote.books.CertainBookResponse
import com.mooduck.data.remote.books.CommentRequest
import com.mooduck.data.remote.books.CommentResponse
import com.mooduck.domain.models.Book
import com.mooduck.domain.models.BooksPage
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

    override suspend fun addLikeToComment(commentId: String): Result<Boolean> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = bookApi.addLikeToComment(commentId)
                    response.isSuccessful
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error add like to comment id:=$commentId", e))
        }
    }

    override suspend fun addDisLikeToComment(commentId: String): Result<Boolean> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = bookApi.addDislikeToComment(commentId)
                    response.isSuccessful
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error add dislike to comment id:=$commentId", e))
        }
    }

    override suspend fun deleteLikeToComment(commentId: String): Result<Boolean> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = bookApi.deleteLikeFromComment(commentId)
                    response.isSuccessful
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error delete like to comment id:=$commentId", e))
        }
    }

    override suspend fun deleteDisLikeToComment(commentId: String): Result<Boolean> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = bookApi.deleteDislikeFromComment(commentId)
                    response.isSuccessful
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error delete dislike to comment id:=$commentId", e))
        }
    }

    override suspend fun addCommentToBook(
        bookId: String,
        title: String,
        text: String,
        rating: Int
    ): Result<Boolean> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = bookApi.addCommentToBook(id = bookId, params = CommentRequest(title,text, rating))
                    response.isSuccessful
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error add comment to book id:=$bookId", e))
        }
    }

    override suspend fun getBooks(
        text: String?,
        limit: Int?,
        page: Int?,
        genre: String?,
        author: String?
    ): Result<BooksPage> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = bookApi.getBooks(text,limit,page,genre,author)
                    response.await()
                }.toBooksPage()
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