package com.mooduck.domain.repository

import com.mooduck.domain.models.BooksPage
import com.mooduck.domain.models.Result
import com.mooduck.domain.models.AuthUser
import com.mooduck.domain.models.User

interface RemoteUserRepository {
    suspend fun setFavoriteBook(bookId: String, userId: String): Result<Boolean>
    suspend fun deleteFavoriteBook(bookId: String, userId: String): Result<Boolean>


    suspend fun getFavouriteBooks(userId: String, limit: Int, page: Int,): Result<BooksPage>

    suspend fun getUserById(userId: String): Result<User>
}