package com.newsandfeed.ui.features.topheadline

import com.newsandfeed.domain.usecase.topheadlines.GetAllTopHeadLinesUseCase
import com.newsandfeed.ui.features.topheadline.mvi.HomeState
import com.newsandfeed.ui.features.topheadline.mvi.HomeIntent
import com.newsandfeed.util.CFlow
import com.newsandfeed.util.CoroutineViewModel
import com.newsandfeed.util.HAS_NOT_CONNECTION_INTERNET
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
    val initState = _state.value


    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadTopHeadlines -> loadTopHeadlines()
        }
    }

    //wrapper para ios
    fun subscribe(block: (HomeState) -> Unit) = cState.subscribe(block, scope)

    private fun loadTopHeadlines() = scope.launch {
        getTopHeadlineUseCase()
            .onStart {
                _state.update {
                    it.copy(
                        isLoading = true,
                        showToastErrorIfNotConnectionInternet = false
                    )
                }
            }
            .catch { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = error.message,
                        showToastErrorIfNotConnectionInternet = error.message == HAS_NOT_CONNECTION_INTERNET
                    )
                }
            }
            .collect { dataOrException ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        topHeadlines = dataOrException.data ?: it.topHeadlines,
                        error = dataOrException.exception?.message,
                        showToastErrorIfNotConnectionInternet = dataOrException.exception?.message == HAS_NOT_CONNECTION_INTERNET
                    )
                }
            }
    }
}





