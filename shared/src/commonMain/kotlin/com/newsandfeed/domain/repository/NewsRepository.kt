package com.newsandfeed.domain.repository

import com.newsandfeed.domain.entity.DataOrException
import com.newsandfeed.domain.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getAllCurrentNews(): Flow<DataOrException<NewsEntity?, Boolean, Exception>>
}