package com.momen.orangetask.repository

import com.momen.orangetask.api.BooksApiService
import com.momen.orangetask.models.BooksResponse
import retrofit2.Response
import javax.inject.Inject

class BooksRepository @Inject constructor(
    private val apiService: BooksApiService
) {
    suspend fun fetchBooks(query: String, apiKey: String): Response<BooksResponse> {
        return apiService.getBooks(query, apiKey)
    }
}