package com.mooduck.domain.repository

import com.mooduck.domain.models.AuthUser

interface LocalUserRepository {


    suspend fun saveUser(authUser: AuthUser)

    suspend fun getUser(): AuthUser?

    suspend fun getUserId(): String?

    fun setTokens(accessToken: String, refreshToken: String)
    fun getRefreshToken(): String?

    fun getAccessToken(): String?

    fun clearUser()
}