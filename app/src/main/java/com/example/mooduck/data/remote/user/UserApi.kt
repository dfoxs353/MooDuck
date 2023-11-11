package com.example.mooduck.data.remote.user

import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {

    @POST("users/{userId}/favoritebooks")
    fun setFavoriteBooks(
        @Body bookId: String,
        @Path("userId") userId: String,
    ):Deferred<UserSetFavouriteBookResult>

    @GET("users/{userId}/favoritebooks")
    fun getFavoriteBooks(
        @Body bookId: String,
        @Path("userId") userId: String,
    ):Deferred<UserSetFavouriteBookResult>
}