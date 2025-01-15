package com.momen.orangetask.api

import com.momen.orangetask.models.BooksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApiService {
    @GET("books/v1/volumes")
    suspend fun getBooks(
        @Query("q") query: String,
        @Query("key") apiKey: String
    ): Response<BooksResponse>
}