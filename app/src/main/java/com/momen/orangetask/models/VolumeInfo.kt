package com.momen.orangetask.models

data class VolumeInfo(
    val title: String,
    val authors: List<String>?,
    val description: String?,
    val publishedDate: String?,
    val imageLinks: ImageLinks?
)
