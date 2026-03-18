package com.checkout.android.components.sample.core.network.model

/**
 * A sealed class representing the result of a network request.
 *
 * @param T The type of the data returned in case of a successful response.
 */
sealed class ApiResult<T>

/**
 * A request that resulted in a response with a 2xx status code that has a body.
 */
data class ApiSuccess<T>(
  val data: T,
) : ApiResult<T>()

/**
 * A request that resulted in a response with a non-2xx status code, no response body, or due to network failure.
 */
data class ApiHttpError(
  val code: Int,
  val message: String?,
) : ApiResult<Nothing>()

/**
 * A request that didn't result in a response due to unexpected error.
 * For instance, parsing json object.
 */
data class ApiError(
  val error: Throwable? = null,
) : ApiResult<Nothing>()
