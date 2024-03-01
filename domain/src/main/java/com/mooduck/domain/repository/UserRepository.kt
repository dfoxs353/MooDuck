package com.mooduck.domain.repository

import com.mooduck.domain.models.Book
import com.mooduck.domain.models.User

interface UserRepository {

    suspend fun setFavoriteBook(bookId: String, userId: String): Result<Boolean>

    suspend fun getFavouriteBooks(userId: String): Result<List<Book>>

    suspend fun saveUser(): Result<User>

    suspend fun getUser(): Result<User?>

    fun getRefreshToken(): String?

    fun getUserId(): String?

    fun getAccessToken(): String?

    fun clearUser()
}