package com.checkout.android.components.sample.core.network.repository

import com.checkout.android.components.sample.core.BuildConfig
import com.checkout.android.components.sample.core.Constants
import com.checkout.android.components.sample.core.api.PaymentSessionApi
import com.checkout.android.components.sample.core.model.PaymentSessionResult
import com.checkout.android.components.sample.core.model.SubmitPaymentSessionResult
import com.checkout.android.components.sample.core.network.extension.safeCall
import com.checkout.android.components.sample.core.network.model.ApiError
import com.checkout.android.components.sample.core.network.model.ApiHttpError
import com.checkout.android.components.sample.core.network.model.ApiSuccess
import com.checkout.android.components.sample.core.network.model.session.PaymentSessions
import com.checkout.android.components.sample.core.network.model.session.SubmitPaymentSession
import com.checkout.components.interfaces.Environment
import com.checkout.components.interfaces.model.paymentsession.PaymentAction
import javax.inject.Inject

class PaymentSessionRepository @Inject constructor(
  private val paymentSessionApi: PaymentSessionApi,
) {
  /**
   * Creates a new payment session by making a network request to the payment session API.
   *
   * @param environment The [Environment] to target (Sandbox or Production). Defaults to [Environment.SANDBOX].
   * @param paymentSessions The [PaymentSessions] configuration data required to initialize the session.
   * @return A [PaymentSessionResult] containing the session ID and secret on success, or error details on failure.
   */
  suspend fun createPaymentSession(
    environment: Environment = Environment.SANDBOX,
    paymentSessions: PaymentSessions,
  ): PaymentSessionResult {
    val baseUrl = getPaymentSessionUrl(environment)

    val apiResult = safeCall {
      paymentSessionApi.postPaymentSession(
        url = baseUrl,
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

  /**
   * Submits a payment session to the backend API.
   *
   * @param environment The [Environment] to target (defaults to [Environment.SANDBOX]).
   * @param submitPaymentSession The [SubmitPaymentSession] data containing payment details.
   * @param paymentSessionId The unique identifier of the payment session to submit.
   * @return A [SubmitPaymentSessionResult] representing the success or failure of the operation.
   */
  suspend fun submitPaymentSession(
    environment: Environment = Environment.SANDBOX,
    submitPaymentSession: SubmitPaymentSession,
    paymentSessionId: String,
  ): SubmitPaymentSessionResult {
    val baseUrl = getSubmitPaymentSessionUrl(environment, paymentSessionId)

    val apiResult = safeCall {
      paymentSessionApi.postSubmitPaymentSession(
        url = baseUrl,
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
          action = apiResult.data.action?.let {
            PaymentAction(
              type = it.type,
              url = it.url,
            )
          },
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

  private fun getPaymentSessionUrl(environment: Environment): String = when (environment) {
    Environment.SANDBOX -> "${Constants.API_SANDBOX_URL}/payment-sessions"
    Environment.PRODUCTION -> "${Constants.API_PRODUCTION_URL}/payment-sessions"
  }
}

private fun getSubmitPaymentSessionUrl(environment: Environment, paymentSessionId: String): String = when (environment) {
  Environment.SANDBOX -> "${Constants.API_SANDBOX_URL}/payment-sessions/$paymentSessionId/submit"
  Environment.PRODUCTION -> "${Constants.API_PRODUCTION_URL}/payment-sessions/$paymentSessionId/submit"
}
