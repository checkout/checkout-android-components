package com.checkout.android.components.sample.core.network.model.session

import com.checkout.android.components.sample.core.model.PaymentItem
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SubmitPaymentSession(
  val amount: Int,
  val reference: String = "Reference",
  @SerialName("session_data")
  val sessionData: String,
  @EncodeDefault(EncodeDefault.Mode.NEVER)
  val items: List<PaymentItem>? = null,
  @SerialName("3ds")
  val threeDS: ThreeDS = ThreeDS(),
)
