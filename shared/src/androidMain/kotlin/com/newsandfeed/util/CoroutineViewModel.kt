package com.newsandfeed.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

actual  open class CoroutineViewModel: ViewModel() {

    actual val scope: CoroutineScope = viewModelScope

}