package com.newsandfeed.domain.repository

import com.newsandfeed.domain.entity.DataOrException
import com.newsandfeed.domain.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getAllArticles(): Flow<DataOrException<ArticleEntity?, Boolean, Exception>>
    fun getOnlyTopicArticles(word: String): Flow<DataOrException<ArticleEntity?, Boolean, Exception>>
}