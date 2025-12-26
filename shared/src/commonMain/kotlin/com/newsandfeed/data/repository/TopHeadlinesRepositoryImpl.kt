package com.newsandfeed.data.repository

import com.newsandfeed.data.remote.RemoteDataSource
import com.newsandfeed.domain.entity.ArticleEntity
import com.newsandfeed.domain.entity.DataOrException
import com.newsandfeed.domain.mapper.articles.toDomain
import com.newsandfeed.domain.repository.TopHeadlinesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TopHeadlinesRepositoryImpl : TopHeadlinesRepository, KoinComponent {
    private val remoteDataSource by inject<RemoteDataSource>()

    override fun getTopHeadlines(): Flow<DataOrException<ArticleEntity?, Boolean, Exception>> =
        flow {
            val result = remoteDataSource.fetchTopHeadlines()
            emit(
                DataOrException<ArticleEntity?, Boolean, Exception>(
                    data = result.data?.toDomain(),
                    isLoading = result.isLoading,
                    exception = result.exception
                )
            )
        }.flowOn(Dispatchers.IO)
}