package com.mooduck.domain.repository

import com.mooduck.domain.models.Book
import com.mooduck.domain.models.Result

interface RemoteUserRepository {
    suspend fun setFavoriteBook(bookId: String, userId: String): Result<Boolean>

    suspend fun getFavouriteBooks(userId: String): Result<List<Book>>
}