package com.mooduck.data.repository

import android.util.Log
import com.mooduck.data.local.LocalUserDataSource
import com.mooduck.data.mappers.toBooks
import com.mooduck.data.remote.user.UserApi
import com.mooduck.domain.models.Book
import com.mooduck.domain.models.Result
import com.mooduck.domain.repository.RemoteUserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException

class RemoteUserRepositoryImpl(
    private val userDataSource: UserApi,
    private val ioDispatcher: CoroutineDispatcher
) : RemoteUserRepository {

    override suspend fun setFavoriteBook(bookId: String, userId: String): Result<Boolean> {
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

    override suspend fun getFavouriteBooks(userId: String): Result<List<Book>> {
        return try {
            Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.getFavoriteBooks(userId)
                    response.await()
                }.toBooks()
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            Result.Error(IOException("Error get Favourite books $userId", e))
        }
    }

}