package com.mooduck.data.remote.books

import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApi {

    @GET("books")
    fun getBooks(
        @Query("text") text: String? = null,
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = null,
        @Query("genre") genre: String? = null,
        @Query("author") author: String? = null
    ):Deferred<BooksResponse>

    @GET("books/{id}")
    fun getBook(@Path("id") id: String):Deferred<CertainBookResponse>

    @GET("books/{id}/comments")
    fun getBookComments(@Path("id") id: String):Deferred<List<CommentResponse>>

    @POST("books/{id}/comments")
    suspend fun addCommentToBook(
        @Path("id") id: String,
        @Body params: CommentRequest
    ): Response<ResponseBody>
    @POST("comments/{id}/likes")
    suspend fun addLikeToComment(
        @Path("id") id: String
    ): Response<Any>

    @DELETE("comments/{id}/likes")
    suspend fun deleteLikeFromComment(
        @Path("id") id: String
    ): Response<Any>

    @POST("comments/{id}/dislikes")
    suspend fun addDislikeToComment(
        @Path("id") id: String
    ): Response<Any>
    @DELETE("comments/{id}/dislikes")
    suspend fun deleteDislikeFromComment(
        @Path("id") id: String
    ): Response<Any>
}