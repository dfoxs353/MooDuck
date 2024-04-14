package com.mooduck.domain.repository

import com.mooduck.domain.models.Book
import com.mooduck.domain.models.User
import com.mooduck.domain.models.Result

interface LocalUserRepository {


    suspend fun saveUser(user: User)

    suspend fun getUser(): User?

    suspend fun getUserId(): String?
    fun getRefreshToken(): String?

    fun getAccessToken(): String?

    fun clearUser()
}