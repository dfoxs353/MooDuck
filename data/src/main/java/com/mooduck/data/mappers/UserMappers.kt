package com.mooduck.data.mappers

import com.mooduck.data.remote.auth.AuthResponse
import com.mooduck.data.remote.user.UserResponse
import com.mooduck.domain.models.AuthUser
import com.mooduck.domain.models.User

internal fun AuthResponse.toAuthUser() : AuthUser{
    return AuthUser(
        userid = this.user.id,
        userName = this.user.username,
        userPassword = "",
        refreshToken = this.refreshToken,
        accessToken = this.accessToken,
    )
}

internal fun UserResponse.toUser() : User{
    return User(
        id = this._id,
        userName = this.username,
        email = this.email
    )
}