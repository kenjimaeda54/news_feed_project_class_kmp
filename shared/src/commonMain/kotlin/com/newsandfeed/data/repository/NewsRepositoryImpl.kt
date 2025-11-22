package com.newsandfeed.data.repository

import com.newsandfeed.data.remote.RemoteDataSource
import com.newsandfeed.domain.entity.DataOrException
import com.newsandfeed.domain.entity.NewsEntity
import com.newsandfeed.domain.mapper.articles.toDomain
import com.newsandfeed.domain.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class NewsRepositoryImpl : NewsRepository, KoinComponent {
    private val remoteDataSource by inject<RemoteDataSource>()

    override fun getAllCurrentNews(): Flow<DataOrException<NewsEntity?, Boolean, Exception>> =
        flow {
            val result = remoteDataSource.fetchAllCurrentNews()
            emit(
                DataOrException<NewsEntity?, Boolean, Exception>(
                    data = result.data?.toDomain(),
                    isLoading = result.isLoading,
                    exception = result.exception
                )
            )
        }.flowOn(Dispatchers.IO)
}