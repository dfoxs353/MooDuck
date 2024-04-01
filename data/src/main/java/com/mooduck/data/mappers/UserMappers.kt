package com.mooduck.data.mappers

import com.mooduck.data.remote.auth.AuthResponse
import com.mooduck.domain.models.User

internal fun AuthResponse.toUser() : User{
    return User(
        userid = this.user.id,
        userPassword = "",
        refreshToken = this.refreshToken,
        accessToken = this.accessToken,
    )
}