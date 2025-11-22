package com.newsandfeed.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

actual  open  class CoroutineViewModel {

    actual val scope = CoroutineScope(
        Dispatchers.IO + SupervisorJob()
    )

    fun clear() {
        scope.cancel()
    }
}