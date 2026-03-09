package com.checkout.android.components.sample.core.api

import com.checkout.android.components.sample.core.session.request.PaymentSessions
import com.checkout.android.components.sample.core.session.request.SubmitPaymentSession
import com.checkout.android.components.sample.core.session.response.PaymentSessionsResponse
import com.checkout.android.components.sample.core.session.response.SubmitPaymentSessionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentSessionApi {
  @POST("/payment-sessions")
  suspend fun postPaymentSession(
    @Header("Content-Type") contentType: String = "application/json",
    @Header("Authorization") token: String,
    @Body body: PaymentSessions,
  ): Response<PaymentSessionsResponse>

  @POST("/payment-sessions/{id}/submit")
  suspend fun postSubmitPaymentSession(
    @Header("Content-Type") contentType: String = "application/json",
    @Header("Authorization") token: String,
    @Path("id") paymentSessionId: String,
    @Body body: SubmitPaymentSession,
  ): Response<SubmitPaymentSessionResponse>
}
