package com.newsandfeed.ui.features.topheadline

import com.newsandfeed.domain.usecase.topheadlines.GetAllTopHeadLinesUseCase
import com.newsandfeed.ui.features.topheadline.mvi.HomeState
import com.newsandfeed.ui.features.topheadline.mvi.HomeIntent
import com.newsandfeed.util.CFlow
import com.newsandfeed.util.CoroutineViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : CoroutineViewModel(), KoinComponent {
    private val getTopHeadlineUseCase by inject<GetAllTopHeadLinesUseCase>()
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    //wrap para ios
    val cState = CFlow(state)


    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadTopHeadlines -> loadTopHeadlines()
        }
    }

    private fun loadTopHeadlines() = scope.launch {
        getTopHeadlineUseCase()
            .onStart {
                _state.update {
                    it.copy(
                        isLoading = true,
                    )
                }
            }
            .catch { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.message
                    )
                }
            }
            .collect { dataOrException ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        topHeadlines = dataOrException.data ?: it.topHeadlines,
                        error = dataOrException.exception?.message,
                    )
                }
            }
    }
}





