package com.newsandfeed.data.repository

import com.newsandfeed.data.local.ArticleLocalDataSource
import com.newsandfeed.data.remote.RemoteDataSource
import com.newsandfeed.domain.entity.DataOrException
import com.newsandfeed.domain.entity.ArticleEntity
import com.newsandfeed.domain.mapper.articles.toDomain
import com.newsandfeed.domain.repository.ArticleRepository
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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ArticleRepositoryImpl : ArticleRepository, KoinComponent {
    private val remoteDataSource by inject<RemoteDataSource>()
    private val localDataSource by inject<ArticleLocalDataSource>()
    private val connectivityObserver by inject<ConnectivityObserver>()

    override fun getAllArticles(): Flow<DataOrException<ArticleEntity?, Boolean, Exception>> =
        netWorkBoundResource(
            loadFromDb = {
                val list = localDataSource.selectAllArticleWithContent()
                if (list.isNotEmpty()) ArticleEntity(
                    content = list.map { articleWithContent ->
                        articleWithContent.toDomain()
                    }
                ) else null

            },
            isCacheValid = { data ->
                val lastest = localDataSource.selectedLastestCacheTimeStamp()
                lastest?.createAt?.let { !isExpiredData(it, 30) } ?: false
            },
            fetch = {
                remoteDataSource.fetchAllArticles()
            },
            saveFetchResult = {
                localDataSource.insertArticle(it?.toDomain() ?: ArticleEntity(emptyList()))
            },
            hasInternet = {
                connectivityObserver.observe().first() == NetWorkStatus.Available
            }

        )

    override fun getOnlyTopicArticles(word: String): Flow<DataOrException<ArticleEntity?, Boolean, Exception>> =
        flow {
            val result = remoteDataSource.searchOnlyTopicArticle(word)
            emit(
                DataOrException<ArticleEntity?, Boolean, Exception>(
                    data = result.data?.toDomain(),
                    isLoading = result.isLoading,
                    exception = result.exception
                )
            )
        }.flowOn(Dispatchers.IO)
}