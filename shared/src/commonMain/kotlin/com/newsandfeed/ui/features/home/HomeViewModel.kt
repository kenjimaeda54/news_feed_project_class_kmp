package com.newsandfeed.ui.features.home

import com.newsandfeed.domain.entity.NewsEntity
import com.newsandfeed.domain.usecase.home.GetCurrentNewsUseCase
import com.newsandfeed.ui.features.home.mvi.HomeIntent
import com.newsandfeed.ui.features.home.mvi.HomeState
import com.newsandfeed.util.CoroutineViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : CoroutineViewModel(), KoinComponent {
    private val getCurrentNewsUseCase by inject<GetCurrentNewsUseCase>()
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        handleIntent(HomeIntent.LoadItems)
    }

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadItems -> loadCurrentNews()
        }
    }

    private fun loadCurrentNews() = scope.launch {
        getCurrentNewsUseCase()
            .catch { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        currentNews = NewsEntity(emptyList()),
                        error = error.message,
                    )
                }
            }
            .collect { result ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        currentNews = result.data ?: it.currentNews,
                        error = null
                    )
                }
            }
    }
}