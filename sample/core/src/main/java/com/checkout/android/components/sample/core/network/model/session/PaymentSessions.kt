package com.checkout.android.components.sample.core.network.model.session

import com.checkout.android.components.sample.core.model.Address
import com.checkout.android.components.sample.core.model.Billing
import com.checkout.android.components.sample.core.model.PaymentItem
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentSessions(
  val amount: Int = 100,
  val currency: String = "GBP",
  val billing: Billing = Billing(Address("GB")),
  @SerialName("success_url")
  val successUrl: String = "https://success_calback",
  @SerialName("failure_url")
  val failureUrl: String = "https://failure_calback",
  @SerialName("processing_channel_id")
  @EncodeDefault(EncodeDefault.Mode.NEVER)
  val processingChannelID: String? = null,
  @SerialName("enabled_payment_methods")
  val enabledPaymentMethods: List<String>,
  val items: List<PaymentItem> = listOf(PaymentItem("Item 1", 1, 100)),
  @SerialName("3ds")
  val threeDS: ThreeDS = ThreeDS(),
  val locale: String? = null,
)

@Serializable
data class ThreeDS(
  val enabled: Boolean = true,
)
