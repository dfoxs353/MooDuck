package com.example.mooduck.data.repository

import android.util.Log
import com.example.mooduck.data.remote.Result
import com.example.mooduck.data.remote.auth.AuthApi
import com.example.mooduck.data.remote.auth.UserLoginRequest
import com.example.mooduck.data.remote.user.UserApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException

class RemoteUserRepository(
    private val userDataSource: UserApi,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun setFavouriteBook(bookId: String, userId: String): Result<Boolean> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.addBookToFavorite(bookId, userId)
                    response.await()
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error setFavourite book id $bookId", e))
        }
    }

    suspend fun getFavouriteBook(userId: String): Result<Any> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.getFavoriteBooks(userId)
                    response.await()
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error get Favourite books $userId", e))
        }
    }
}