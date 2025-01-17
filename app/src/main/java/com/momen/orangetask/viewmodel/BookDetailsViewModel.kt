package com.momen.orangetask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.momen.orangetask.models.Book
import com.momen.orangetask.models.VolumeInfo
import com.momen.orangetask.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val repository: BooksRepository
) : ViewModel() {

    private val _volumeInfo = MutableLiveData<VolumeInfo>()
    val volumeInfo: LiveData<VolumeInfo> = _volumeInfo

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error
    fun onErrorMessageShown() {
        _error.value = null
    }

    private val _noInternet = MutableLiveData<Boolean>()
    val noInternet: LiveData<Boolean> = _noInternet

    fun onNoInternetShown() {
        _noInternet.value = false
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getBookById(bookId: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val response = repository.fetchBookDetails(bookId)
                if (response.isSuccessful) {
                    _volumeInfo.value = response.body()?.volumeInfo
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                if (e.message?.contains("Unable to resolve host") == true)
                    _noInternet.value = true
                else
                    _error.value = "Error: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }
}