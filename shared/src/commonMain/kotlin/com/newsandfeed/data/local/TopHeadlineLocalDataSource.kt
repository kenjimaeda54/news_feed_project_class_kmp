package com.newsandfeed.data.local

import com.newsandfeed.database.NewsDatabase
import com.newsandfeed.database.SelectTopHeadlinesWithContentTopHeadlines
import com.newsandfeed.domain.entity.ArticleEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class TopHeadlineLocalDataSource: KoinComponent {
    private val newsDatabase by inject<NewsDatabase>()

    @OptIn(ExperimentalUuidApi::class)
    fun insertTopHeadlines(articleEntity: ArticleEntity) {
        val id = Uuid.random().toString()

        newsDatabase.transaction {

            newsDatabase.topHeadlinesQueries.deleteAllTopHeadlines()

            newsDatabase.topHeadlinesQueries.insertTopHeadlines(
                id = id
            )

            articleEntity.content.forEach { articleEntity ->
                newsDatabase.topHeadlinesQueries.insertContentTopHeadlines(
                    topHeadlinesId = id,
                    author = articleEntity.author,
                    content = articleEntity.content,
                    description = articleEntity.description,
                    title = articleEntity.title,
                    url = articleEntity.url,
                    urlToImage = articleEntity.urlToImage
                )

            }
        }
    }

    fun selectedLastestCacheTimeStamp(): String? {
        return newsDatabase.topHeadlinesQueries.selectLastCreatedAt().executeAsOneOrNull()?.createAt
    }

    fun selectAllTopHeadlinesWithContent(): List<SelectTopHeadlinesWithContentTopHeadlines> {
        return newsDatabase.topHeadlinesQueries.selectTopHeadlinesWithContentTopHeadlines().executeAsList()
    }


}