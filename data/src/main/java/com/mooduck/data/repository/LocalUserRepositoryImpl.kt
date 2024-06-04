package com.mooduck.data.repository

import com.mooduck.data.local.LocalUserDataSource
import com.mooduck.domain.models.AuthUser
import com.mooduck.domain.repository.LocalUserRepository

class LocalUserRepositoryImpl (
    private val userDataSource: LocalUserDataSource,
) : LocalUserRepository {


    override suspend fun saveUser(authUser: AuthUser) {
        return userDataSource.saveUser(authUser)
    }

    override suspend fun getUser(): AuthUser? {
        return userDataSource.getUser()
    }

    override suspend fun getUserId(): String? {
        return userDataSource.getUserId()
    }

    override fun getRefreshToken(): String? {
        return userDataSource.getRefreshToken()
    }

    override fun getAccessToken(): String? {
        return userDataSource.getAccessToken()
    }

    override fun clearUser() {
        userDataSource.clearUserData()
    }

    override fun setTokens(accessToken: String, refreshToken: String) {
        userDataSource.saveJWToken(accessToken,refreshToken)
    }
}