package com.example.mooduck.data.repository

import android.util.Log
import com.example.mooduck.data.local.LocalUserDataSource
import com.mooduck.domain.models.Result
import com.example.mooduck.data.remote.books.BooksResponse
import com.example.mooduck.data.remote.user.UserApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException

class UserRepository(
    private val remoteUserDataSource: UserApi,
    private val localUserDataSource: LocalUserDataSource,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun setFavouriteBook(bookId: String, userId: String): Result<Boolean> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = remoteUserDataSource.addBookToFavorite(bookId, userId)
                    response.await()
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error setFavourite book id $bookId", e))
        }
    }

    suspend fun getFavouriteBooks(userId: String): Result<BooksResponse> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = remoteUserDataSource.getFavoriteBooks(userId)
                    response.await()
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error get Favourite books $userId", e))
        }
    }
}