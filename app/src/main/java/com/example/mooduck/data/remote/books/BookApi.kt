package com.example.mooduck.data.remote.books

import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface BookApi {

    @GET("books")
    fun getBooks(
        @Query("limit") limit: Int? = null,
        @Query("page") page: Int? = null,
        @Query("genre") genre: String? = null,
        @Query("author") author: String? = null
    ):Deferred<BooksResponse>

    @GET("books/{id}")
    fun getBook(@Path("id") id: String):Deferred<CertainBookResponse>

    @GET("books/{id}/comments")
    fun getBookComments(@Path("id") id: String):Deferred<List<Comment>>
}