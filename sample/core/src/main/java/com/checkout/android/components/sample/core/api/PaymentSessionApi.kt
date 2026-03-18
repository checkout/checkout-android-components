package com.checkout.android.components.sample.core.api

import com.checkout.android.components.sample.core.Constants
import com.checkout.android.components.sample.core.Constants.CONTENT_TYPE_JSON
import com.checkout.android.components.sample.core.Constants.HEADER_AUTHORIZATION
import com.checkout.android.components.sample.core.Constants.HEADER_CONTENT_TYPE
import com.checkout.android.components.sample.core.Constants.HEADER_ID
import com.checkout.android.components.sample.core.network.model.session.PaymentSessions
import com.checkout.android.components.sample.core.network.model.session.PaymentSessionsResponse
import com.checkout.android.components.sample.core.network.model.session.SubmitPaymentSession
import com.checkout.android.components.sample.core.network.model.session.SubmitPaymentSessionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface PaymentSessionApi {
  @POST
  suspend fun postPaymentSession(
    @Url url: String = Constants.API_SANDBOX_URL,
    @Header(HEADER_CONTENT_TYPE) contentType: String = CONTENT_TYPE_JSON,
    @Header(HEADER_AUTHORIZATION) token: String,
    @Body body: PaymentSessions,
  ): Response<PaymentSessionsResponse>

  @POST
  suspend fun postSubmitPaymentSession(
    @Url url: String = Constants.API_SANDBOX_URL,
    @Header(HEADER_CONTENT_TYPE) contentType: String = CONTENT_TYPE_JSON,
    @Header(HEADER_AUTHORIZATION) token: String,
    @Path(HEADER_ID) paymentSessionId: String,
    @Body body: SubmitPaymentSession,
  ): Response<SubmitPaymentSessionResponse>
}
