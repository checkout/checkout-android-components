package com.checkout.android.components.sample.core.session.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubmitPaymentSessionResponse(
  val id: String,
  val type: String,
  val status: String,
  val action: PaymentAction? = null,
  @SerialName("decline_reason")
  val declineReason: String? = null,
)

@Serializable
data class PaymentAction(
  val type: String,
  val url: String? = null,
)
