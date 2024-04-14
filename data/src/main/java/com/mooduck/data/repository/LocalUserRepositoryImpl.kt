package com.mooduck.data.repository

import com.mooduck.data.local.LocalUserDataSource
import com.mooduck.domain.models.User
import com.mooduck.domain.repository.LocalUserRepository

class LocalUserRepositoryImpl (
    private val userDataSource: LocalUserDataSource,
) : LocalUserRepository {


    override suspend fun saveUser(user: User) {
        return userDataSource.saveUser(user)
    }

    override suspend fun getUser(): User? {
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
}