package com.newsandfeed.util

import com.newsandfeed.domain.entity.DataOrException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

inline  fun <ResultType,RequestType> netWorkBoundResource(
    crossinline loadFromDb: suspend  () -> ResultType?,
    crossinline isCacheValid: (ResultType?) -> Boolean,
    crossinline fetch: suspend () -> DataOrException<RequestType,Boolean,Exception>,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline hasInternet: suspend () -> Boolean
) = flow<DataOrException<ResultType?,Boolean,Exception>> {
    val data = loadFromDb()

    when {
        isCacheValid(data) && data != null -> {
            emit(DataOrException(data,isLoading = false,exception = null))
        }

        hasInternet() -> {
            emit(DataOrException(data, isLoading = false, exception = null))

            val apiResponse = fetch()

            if(apiResponse.data != null && apiResponse.exception == null) {
                saveFetchResult(apiResponse.data)
                emit(
                    DataOrException(
                        data = loadFromDb(),
                        isLoading = false,
                        exception = null
                    )
                )
            }else {
                emit(
                    DataOrException(
                        data = data,
                        isLoading = false,
                        exception = apiResponse.exception
                    )
                )
            }
        }

        else -> {
            emit(
                DataOrException(
                    data = data,
                    isLoading = false,
                    exception = Exception(HAS_NOT_CONNECTION_INTERNET)
                )
            )

        }
    }
}.flowOn(Dispatchers.IO)