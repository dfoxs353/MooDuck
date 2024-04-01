package com.mooduck.data.repository

import android.util.Log
import com.mooduck.data.local.LocalUserDataSource
import com.mooduck.data.mappers.toBooks
import com.mooduck.domain.models.Result
import com.mooduck.data.remote.books.BooksResponse
import com.mooduck.data.remote.user.UserApi
import com.mooduck.domain.models.Book
import com.mooduck.domain.models.User
import com.mooduck.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException

class UserRepositoryImpl(
    private val remoteUserDataSource: UserApi,
    private val localUserDataSource: LocalUserDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun setFavoriteBook(bookId: String, userId: String): Result<Boolean> {
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

    override suspend fun getFavouriteBooks(userId: String): Result<List<Book>> {
        return try {
            Result.Success(
                withContext(ioDispatcher) {
                    val response = remoteUserDataSource.getFavoriteBooks(userId)
                    response.await()
                }.toBooks()
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            Result.Error(IOException("Error get Favourite books $userId", e))
        }
    }

    override suspend fun saveUser(user: User) {
        return localUserDataSource.saveUser(user)
    }

    override suspend fun getUser(): User? {
        return localUserDataSource.getUser()
    }

    override suspend fun getUserId(): String? {
        return localUserDataSource.getUserId()
    }

    override fun getRefreshToken(): String? {
        return localUserDataSource.getRefreshToken()
    }

    override fun getAccessToken(): String? {
        return localUserDataSource.getAccessToken()
    }

    override fun clearUser() {
        localUserDataSource.clearUserData()
    }
}