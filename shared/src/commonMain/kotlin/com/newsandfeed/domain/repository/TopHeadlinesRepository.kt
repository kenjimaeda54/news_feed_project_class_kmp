package com.newsandfeed.domain.repository

import com.newsandfeed.domain.entity.ArticleEntity
import com.newsandfeed.domain.entity.DataOrException
import kotlinx.coroutines.flow.Flow

interface  TopHeadlinesRepository {
     fun getTopHeadlines(): Flow<DataOrException<ArticleEntity?, Boolean, Exception>>
}