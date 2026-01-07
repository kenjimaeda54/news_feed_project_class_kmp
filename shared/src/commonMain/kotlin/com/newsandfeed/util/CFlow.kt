package com.newsandfeed.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
class CFlow<T: Any>(private val origin: StateFlow<T>): StateFlow<T> by origin {
    fun subscribe(block: (T) -> Unit,scope: CoroutineScope): Job {
        return scope.launch { origin.collect { block(it) } }
    }
}