package com.checkout.android.components.sample.core

import retrofit2.HttpException
import retrofit2.Response

suspend inline fun <reified T> safeCall(block: suspend () -> Response<T>): ApiResult<out T> = try {
  val response = block()
  if (response.isSuccessful && response.body() != null) {
    ApiSuccess(response.body()!!)
  } else {
    ApiError()
  }
} catch (error: HttpException) {
  ApiHttpError(code = error.code(), message = error.message)
} catch (error: Throwable) {
  println(error)

  ApiError(error)
}

sealed class ApiResult<T>

class ApiSuccess<T>(
  val data: T,
) : ApiResult<T>()

class ApiHttpError(
  val code: Int,
  val message: String?,
) : ApiResult<Nothing>()

class ApiError(
  val error: Throwable? = null,
) : ApiResult<Nothing>()
