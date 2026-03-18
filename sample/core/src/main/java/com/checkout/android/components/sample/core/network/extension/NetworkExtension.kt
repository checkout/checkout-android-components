package com.checkout.android.components.sample.core.network.extension

import com.checkout.android.components.sample.core.network.model.ApiError
import com.checkout.android.components.sample.core.network.model.ApiHttpError
import com.checkout.android.components.sample.core.network.model.ApiResult
import com.checkout.android.components.sample.core.network.model.ApiSuccess
import retrofit2.HttpException
import retrofit2.Response

/**
 * Executes a network request safely, catching exceptions and mapping the response to an [ApiResult].
 *
 * @param T The type of the response body.
 * @param block A suspending lambda that performs the network request and returns a [Response].
 * @return An [ApiResult] containing either the successful data ([ApiSuccess]),
 * an HTTP error ([ApiHttpError]), or a general exception ([ApiError]).
 */
suspend inline fun <reified T> safeCall(block: suspend () -> Response<T>): ApiResult<out T> = try {
  val response = block()
  if (response.isSuccessful && response.body() != null) {
    ApiSuccess(response.body()!!)
  } else {
    ApiHttpError(code = response.code(), message = response.message())
  }
} catch (error: HttpException) {
  ApiHttpError(code = error.code(), message = error.message)
} catch (error: Throwable) {
  ApiError(error)
}
