package com.momen.orangetask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.momen.orangetask.BuildConfig
import com.momen.orangetask.models.Book
import com.momen.orangetask.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val repository: BooksRepository
) : ViewModel() {

    private val _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>> = _books

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun searchBooks(query: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.fetchBooks(query, apiKey)
                if (response.isSuccessful) {
                    _books.value = response.body()?.items ?: emptyList()
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.localizedMessage}"
            }
        }
    }

    init {
        searchBooks("wwe", BuildConfig.API_KEY)
    }
}