package com.mooduck.data.repository

import android.util.Log
import com.mooduck.data.mappers.toBooksPage
import com.mooduck.data.mappers.toUser
import com.mooduck.data.remote.user.ChangeFavoriteRequest
import com.mooduck.data.remote.user.UserApi
import com.mooduck.domain.models.BooksPage
import com.mooduck.domain.models.Result
import com.mooduck.domain.models.User
import com.mooduck.domain.repository.RemoteUserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.internal.http2.Http2
import retrofit2.http.HTTP
import java.io.IOException

class RemoteUserRepositoryImpl(
    private val userDataSource: UserApi,
    private val ioDispatcher: CoroutineDispatcher
) : RemoteUserRepository {

    override suspend fun setFavoriteBook(bookId: String, userId: String): Result<Boolean> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.addBookToFavorite(userId, ChangeFavoriteRequest(bookId))
                    response.isSuccessful
                }
            )
        }
        catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error set favourite book id $bookId", e))
        }
    }

    override suspend fun getUserById(userId: String): Result<User> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.getUser(userId)
                    response.await()
                }.toUser()
            )
        }
        catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error get user id:= $userId", e))
        }
    }

    override suspend fun deleteFavoriteBook(bookId: String, userId: String): Result<Boolean> {
        try {
            return Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.deleteBookFromFavorite(userId, ChangeFavoriteRequest(bookId))
                    response.isSuccessful
                }
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            return Result.Error(IOException("Error delete favourite book id $bookId", e))
        }
    }

    override suspend fun getFavouriteBooks(userId: String, limit: Int, page: Int): Result<BooksPage> {
        return try {
            Result.Success(
                withContext(ioDispatcher) {
                    val response = userDataSource.getFavoriteBooks(userId)
                    response.await()
                }.toBooksPage()
            )
        } catch (e: Exception) {
            Log.d("TAG", e.message.toString())
            Result.Error(IOException("Error get Favourite books $userId", e))
        }
    }

}