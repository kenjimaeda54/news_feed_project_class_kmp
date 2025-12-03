package com.newsandfeed.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CFlow<T>(private val origin: StateFlow<T>) {
    fun subscribe(block: (T) -> Unit): Job {
        return CoroutineScope(Dispatchers.Main).launch {
            origin.collect { block(it) }
        }
    }
}