package com.checkout.android.components.sample.core

import com.checkout.android.components.sample.core.api.PaymentSessionApi
import com.checkout.android.components.sample.core.di.Production
import com.checkout.android.components.sample.core.di.Sandbox
import com.checkout.android.components.sample.core.model.PaymentSessionResult
import com.checkout.android.components.sample.core.model.SubmitPaymentSessionResult
import com.checkout.android.components.sample.core.session.request.PaymentSessions
import com.checkout.android.components.sample.core.session.request.SubmitPaymentSession
import com.checkout.components.interfaces.Environment
import com.checkout.components.interfaces.model.paymentsession.PaymentAction
import javax.inject.Inject

class PaymentSessionRepository @Inject constructor(
  @Production private val prodApi: PaymentSessionApi,
  @Sandbox private val sandboxApi: PaymentSessionApi,
) {
  suspend fun createPaymentSession(
    environment: Environment = Environment.SANDBOX,
    paymentSessions: PaymentSessions,
  ): PaymentSessionResult {
    val api = getEnvironmentApi(environment)

    val apiResult = safeCall {
      api.postPaymentSession(
        token = getToken(environment),
        body = paymentSessions,
      )
    }

    return when (apiResult) {
      is ApiSuccess -> {
        PaymentSessionResult(
          apiResult.data.id,
          apiResult.data.paymentSessionSecret,
        )
      }

      is ApiHttpError -> {
        PaymentSessionResult(error = "Error creating payment session, ${apiResult.message}, ${apiResult.code}")
      }

      is ApiError -> {
        PaymentSessionResult(error = "Unknown error creating payment session: ${apiResult.error?.message}")
      }
    }
  }

  suspend fun submitPaymentSession(
    environment: Environment = Environment.SANDBOX,
    submitPaymentSession: SubmitPaymentSession,
    paymentSessionId: String,
  ): SubmitPaymentSessionResult {
    val api = getEnvironmentApi(environment)

    val apiResult = safeCall {
      api.postSubmitPaymentSession(
        token = getToken(environment),
        paymentSessionId = paymentSessionId,
        body = submitPaymentSession,
      )
    }

    return when (apiResult) {
      is ApiSuccess -> {
        SubmitPaymentSessionResult(
          id = apiResult.data.id,
          type = apiResult.data.type,
          status = apiResult.data.status,
          declineReason = apiResult.data.declineReason,
          action = apiResult.data.action?.let { PaymentAction(type = it.type, url = it.url) },
        )
      }

      is ApiHttpError -> {
        SubmitPaymentSessionResult(error = "Error submitting payment session, ${apiResult.message}, ${apiResult.code}")
      }

      is ApiError -> {
        SubmitPaymentSessionResult(error = "Unknown error submitting payment session: ${apiResult.error?.message}")
      }
    }
  }

  private fun getToken(environment: Environment): String = when (environment) {
    Environment.SANDBOX -> "Bearer ${BuildConfig.SANDBOX_SECRET_KEY}"
    Environment.PRODUCTION -> "Bearer ${BuildConfig.PRODUCTION_SECRET_KEY}"
  }

  private fun getEnvironmentApi(environment: Environment): PaymentSessionApi = when (environment) {
    Environment.SANDBOX -> sandboxApi
    Environment.PRODUCTION -> prodApi
  }
}
