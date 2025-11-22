package com.newsandfeed.domain.entity

data class DataOrException<T, Boolean, E : Exception>(
    val data: T? = null,
    val isLoading: Boolean,
    var exception: E? = null
)