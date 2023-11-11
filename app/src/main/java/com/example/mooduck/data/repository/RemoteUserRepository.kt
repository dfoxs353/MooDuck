package com.example.mooduck.data.repository

import android.util.Log
import com.example.mooduck.data.remote.Result
import com.example.mooduck.data.remote.auth.AuthApi
import com.example.mooduck.data.remote.auth.UserLoginRequest
import com.example.mooduck.data.remote.user.UserApi
import com.example.mooduck.data.remote.user.UserSetFavouriteBookResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException

class RemoteUserRepository(
    private val userDataSource: UserApi,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun setFavouriteBook(bookId: String, userId: String): Result<UserSetFavouriteBookResult> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.setFavoriteBooks(bookId, userId)
                    response.await()
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error setFavourite book id $bookId", e))
        }
    }
}