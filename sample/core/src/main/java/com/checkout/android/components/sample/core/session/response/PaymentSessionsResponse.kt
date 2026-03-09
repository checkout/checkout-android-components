package com.checkout.android.components.sample.core.session.response
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param id The Payment Sessions unique identifier
 * @param paymentSessionSecret The secret used by Flow to authenticate payment session requests. Do not log or store this value.
 */
@Serializable
data class PaymentSessionsResponse(
  val id: String,
  @SerialName("payment_session_secret")
  val paymentSessionSecret: String,
)
