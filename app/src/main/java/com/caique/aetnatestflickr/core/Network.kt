package com.caique.aetnatestflickr.core

import com.caique.aetnatestflickr.util.ResultState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend inline fun <reified REMOTE, RESULT> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    crossinline remote: suspend () -> Response<REMOTE>,
    crossinline remoteMapper: suspend (REMOTE?) -> RESULT,
): ResultState<RESULT> = withContext(dispatcher) {
    val response = remote()
    var result: RESULT?

    if (response.isSuccessful) {
        val remoteData = response.body()
        result = remoteMapper(remoteData)
        ResultState.Success(result)
    } else {
        ResultState.Error
    }
}
