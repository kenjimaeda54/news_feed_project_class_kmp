package com.newsandfeed.ui.features.article

import com.newsandfeed.domain.entity.ArticleEntity
import com.newsandfeed.domain.usecase.article.GetAllArticleUseCase
import com.newsandfeed.domain.usecase.article.GetOnlyTopicArticleUseCase
import com.newsandfeed.ui.features.home.mvi.ArticleIntent
import com.newsandfeed.ui.features.home.mvi.ArticleState
import com.newsandfeed.util.CFlow
import com.newsandfeed.util.CoroutineViewModel
import com.newsandfeed.util.HAS_NOT_CONNECTION_INTERNET
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration.Companion.milliseconds

private const val DEBOUNCE_TIME_MS = 700

@OptIn(FlowPreview::class)
class ArticleViewModel : CoroutineViewModel(), KoinComponent{
    private val getArticleUseCase by inject<GetAllArticleUseCase>()
    private val getSearchOnlyTopicArticlesUseCase by inject<GetOnlyTopicArticleUseCase>()
    private val _state = MutableStateFlow(ArticleState())
    private val _searchText = MutableStateFlow("")
    val state: StateFlow<ArticleState> = _state.asStateFlow()
    val cState: CFlow<ArticleState> = CFlow(state)

    init {
        scope.launch {
            _searchText.debounce(DEBOUNCE_TIME_MS.milliseconds).flowOn(Dispatchers.Default)
                .collect {
                    if (it.isNotEmpty()) {
                        performSearch(it)
                    }
                }
        }
    }

    fun handleIntent(intent: ArticleIntent) {
        when (intent) {
            is ArticleIntent.LoadIAllArticles -> loadAllArticles()
            is ArticleIntent.SearchArticle -> searchArticles(intent.query)
        }
    }

    private fun searchArticles(query: String) {
        _searchText.value = query
    }


    private fun performSearch(word: String) = scope.launch {
        getSearchOnlyTopicArticlesUseCase(word)
            .onStart {
                _state.update {
                    it.copy(isLoading = true, error = null)
                }
            }
            .catch { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        articles = ArticleEntity(emptyList()),
                        error = error.message,
                    )
                }
            }
            .collect { result ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        articles = result.data ?: it.articles,
                        error = result.exception?.message,
                    )
                }
            }
    }

    private fun loadAllArticles() = scope.launch {
        getArticleUseCase()
            .onStart {
                _state.update {
                    it.copy(
                        showToastErrorIfNotConnectionInternet = false
                    )
                }
            }
            .catch { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        articles = ArticleEntity(emptyList()),
                        error = error.message,
                    )
                }
            }
            .collect { result ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        showToastErrorIfNotConnectionInternet = result.exception?.message == HAS_NOT_CONNECTION_INTERNET,
                        articles = result.data ?: it.articles,
                        error = null
                    )
                }
            }
    }
}