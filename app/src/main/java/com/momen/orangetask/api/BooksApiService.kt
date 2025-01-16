package com.momen.orangetask.api

import com.momen.orangetask.models.BookDetailsResponse
import com.momen.orangetask.models.BooksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApiService {
    @GET("books/v1/volumes")
    suspend fun getBooks(
        @Query("q") query: String
    ): Response<BooksResponse>

    @GET("books/v1/volumes/{id}")
    suspend fun getBookDetails(
        @Path("id") bookId: String
    ): Response<BookDetailsResponse>

}