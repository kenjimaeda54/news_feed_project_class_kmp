package com.newsandfeed.data.local

import com.newsandfeed.database.NewsDatabase
import com.newsandfeed.database.SelectAllArticleWithContent
import com.newsandfeed.database.SelectLastestCreateAt
import com.newsandfeed.domain.entity.ArticleEntity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class LocalDataSource : KoinComponent {
    private val newsDatabase by inject<NewsDatabase>()

    @OptIn(ExperimentalUuidApi::class)
    fun insertArticle(articleEntity: ArticleEntity) {
        val id = Uuid.random().toString()
        newsDatabase.transaction {

            newsDatabase.articleQueries.deleteAllArticles()

            newsDatabase.articleQueries.insertArticle(
                id = id
            )

            articleEntity.content.forEach { articleEntity ->
                newsDatabase.articleQueries.insertContent(
                    articleId = id,
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

    fun selectedLastestCacheTimeStamp(): SelectLastestCreateAt? {
        return newsDatabase.articleQueries.selectLastestCreateAt().executeAsOneOrNull()

    }

    fun selectAllArticleWithContent(): List<SelectAllArticleWithContent> {
        return newsDatabase.articleQueries.selectAllArticleWithContent().executeAsList()
    }

}