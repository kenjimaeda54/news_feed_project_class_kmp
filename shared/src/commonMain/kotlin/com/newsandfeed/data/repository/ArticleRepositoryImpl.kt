package com.newsandfeed.data.repository

import com.newsandfeed.data.local.LocalDataSource
import com.newsandfeed.data.remote.RemoteDataSource
import com.newsandfeed.domain.entity.DataOrException
import com.newsandfeed.domain.entity.ArticleEntity
import com.newsandfeed.domain.mapper.articles.toDomain
import com.newsandfeed.domain.repository.ArticleRepository
import com.newsandfeed.util.ConnectivityObserver
import com.newsandfeed.util.HAS_NOT_CONNECTION_INTERNET
import com.newsandfeed.util.NetWorkStatus
import com.newsandfeed.util.isExpiredData
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
    private val localDataSource by inject<LocalDataSource>()
    private val connectivityObserver by inject<ConnectivityObserver>()

    override fun getAllArticles(): Flow<DataOrException<ArticleEntity?, Boolean, Exception>> =
        flow {
            val lastestTimeStamp = localDataSource.selectedLastestCacheTimeStamp()
            var isCacheValid = false
            lastestTimeStamp?.createAt?.let { securityCreatAt ->
                val isExpired = isExpiredData(securityCreatAt,1)
                isCacheValid = !isExpired
            }
            if (isCacheValid) {
                val cachedContentList = localDataSource.selectAllArticleWithContent()

                if (cachedContentList.isNotEmpty()) {
                    val articleEntity = ArticleEntity(
                        content =  cachedContentList.map { it.toDomain() }
                    )
                    emit(
                        DataOrException<ArticleEntity?, Boolean, Exception>(
                            data = articleEntity,
                            isLoading =  false,
                            exception = null
                        )
                    )
                    return@flow
                }

            }

            val hasInternet = connectivityObserver.observe().first() == NetWorkStatus.Available

            if (hasInternet) {
                val result = remoteDataSource.fetchAllArticles()
                emit(
                    DataOrException<ArticleEntity?, Boolean, Exception>(
                        data = result.data?.toDomain(),
                        isLoading = result.isLoading,
                        exception = result.exception
                    )
                )
                if (result.data != null && result.exception == null){
                    localDataSource.insertArticle(result.data.toDomain())
                }
                return@flow
            }

            val backUpCache = localDataSource.selectAllArticleWithContent()

            val articleFormCache = if (backUpCache.isNotEmpty()) {
                ArticleEntity(content = backUpCache.map { it.toDomain() })
            }else null

            emit(
                DataOrException<ArticleEntity?, Boolean, Exception>(
                    data = articleFormCache,
                    isLoading = false,
                    exception = Exception(HAS_NOT_CONNECTION_INTERNET)
                )
            )

        }.flowOn(Dispatchers.IO)

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