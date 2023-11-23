package com.example.mooduck.data.repository

import android.util.Log
import com.example.mooduck.data.remote.Result
import com.example.mooduck.data.remote.auth.AuthApi
import com.example.mooduck.data.remote.auth.UserLoginRequest
import com.example.mooduck.data.remote.books.BooksResponse
import com.example.mooduck.data.remote.books.BooksResult
import com.example.mooduck.data.remote.user.UserApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException

class RemoteUserRepository(
    private val userDataSource: UserApi,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun addBookToFavourite(bookId: String, userId: String): Result<Boolean> {
        return try {
            Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.addBookToFavorite(bookId, userId)
                    response.await()
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            Result.Error(IOException("Error addFavourite book id $bookId", e))
        }
    }

    suspend fun getFavouriteBooks(userId: String, limit: Int, page: Int): Result<BooksResponse> {
        return try {
            Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.getUserFavoriteBooks(userId, limit , page)
                    response.await()
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            Result.Error(IOException("Error get Favourite books $userId", e))
        }
    }

    suspend fun deleteBookFromFavorite(bookId: String,userId: String): Result<Any>{
        return try {
            Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.deleteBookFromFavorite(bookId, userId)
                    response.await()
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            Result.Error(IOException("Error delete book from favorite id $bookId", e))
        }
    }
}