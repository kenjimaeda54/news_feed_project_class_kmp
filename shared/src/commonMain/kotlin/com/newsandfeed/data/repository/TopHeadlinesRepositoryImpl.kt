package com.newsandfeed.data.repository

import com.newsandfeed.data.local.TopHeadlineLocalDataSource
import com.newsandfeed.data.remote.RemoteDataSource
import com.newsandfeed.domain.entity.ArticleEntity
import com.newsandfeed.domain.entity.DataOrException
import com.newsandfeed.domain.mapper.articles.toDomain
import com.newsandfeed.domain.repository.TopHeadlinesRepository
import com.newsandfeed.util.ConnectivityObserver
import com.newsandfeed.util.NetWorkStatus
import com.newsandfeed.util.isExpiredData
import com.newsandfeed.util.netWorkBoundResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TopHeadlinesRepositoryImpl : TopHeadlinesRepository, KoinComponent {
    private val remoteDataSource by inject<RemoteDataSource>()
    private val topHeadlineLocalDataSource by inject<TopHeadlineLocalDataSource>()
    private val connectivityObserver by inject<ConnectivityObserver>()

    override fun getTopHeadlines(): Flow<DataOrException<ArticleEntity?, Boolean, Exception>> =
        netWorkBoundResource(
            loadFromDb = {
                val list = topHeadlineLocalDataSource.selectAllTopHeadlinesWithContent()
                if (list.isNotEmpty()) ArticleEntity(
                    content = list.map { topHeadlineWithContent ->
                        topHeadlineWithContent.toDomain()
                    }
                ) else null
            },
            fetch = {
                remoteDataSource.fetchTopHeadlines()
            },
            isCacheValid = {
                val latest = topHeadlineLocalDataSource.selectedLastestCacheTimeStamp()
                latest?.let { !isExpiredData(it, 30) } ?: false
            },
            hasInternet = {
                connectivityObserver.observe().first() == NetWorkStatus.Available
            },
            saveFetchResult = {
                topHeadlineLocalDataSource.insertTopHeadlines(
                    it?.toDomain() ?: ArticleEntity(
                        emptyList()
                    )
                )
            }
        )

}