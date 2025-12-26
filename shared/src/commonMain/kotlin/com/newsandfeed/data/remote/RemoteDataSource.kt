package com.newsandfeed.data.remote

import NewsAndFeed.shared.BuildConfig
import com.newsandfeed.data.dto.NewsDto
import com.newsandfeed.domain.entity.DataOrException
import com.newsandfeed.infra.remote.KtorApi
import io.ktor.client.call.body
import io.ktor.client.request.get

class RemoteDataSource(private val ktorApi: KtorApi) : KtorApi by ktorApi {
    val apiKey = BuildConfig.API_KEY

    suspend fun fetchAllArticles(): DataOrException<NewsDto?, Boolean, Exception> {
        return try {
            val response =
                httpClient.get("/v2/everything?sources=blasting-news-br,info-money,globogoogle-news-br,&pageSize=10&apiKey=$apiKey")
            DataOrException(data = response.body(), isLoading = false)
        } catch (e: Exception) {
            DataOrException(data = null, isLoading = false, exception = e)
        }
    }

    suspend fun searchOnlyTopicArticle(word: String): DataOrException<NewsDto?, Boolean, Exception> {
        return try {
            val response =
                httpClient.get("/v2/everything?q=$word&sources=blasting-news-br,info-money,globogoogle-news-br,&pageSize=10&apiKey=$apiKey")
            DataOrException(data = response.body(), isLoading = false)
        } catch (e: Exception) {
            DataOrException(data = null, isLoading = false, exception = e)
        }
    }

    suspend fun fetchTopHeadlines(): DataOrException<NewsDto?, Boolean, Exception> {
        return try {
            val response =
                httpClient.get("/v2/everything?domains=g1.globo.com,uol.com.br,cnnbrasil.com.br&language=pt&sortBy=publishedAt&pageSize=10&apikey=$apiKey")
            DataOrException(data = response.body(), isLoading = false)
        } catch (e: Exception) {
            DataOrException(data = null, isLoading = false, exception = e)
        }
    }

}