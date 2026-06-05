package com.checkout.android.components.sample.core.network.model.session

import com.checkout.android.components.sample.core.model.Address
import com.checkout.android.components.sample.core.model.AddressAndPhone
import com.checkout.android.components.sample.core.model.Customer
import com.checkout.android.components.sample.core.model.PaymentItem
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentSessions(
  val amount: Int = 10500,
  val currency: String = "GBP",
  val customer: Customer,
  val billing: AddressAndPhone = AddressAndPhone(Address("GB"), null),
  val shipping: AddressAndPhone = AddressAndPhone(Address("GB"), null),
  @SerialName("success_url")
  val successUrl: String = "https://success_calback",
  @SerialName("failure_url")
  val failureUrl: String = "https://failure_calback",
  @SerialName("processing_channel_id")
  @EncodeDefault(EncodeDefault.Mode.NEVER)
  val processingChannelId: String? = null,
  @SerialName("enabled_payment_methods")
  val enabledPaymentMethods: List<String>,
  val items: List<PaymentItem> = listOf(PaymentItem("Item 1", 1, 100)),
  @SerialName("3ds")
  val threeDS: ThreeDS = ThreeDS(),
  val locale: String? = null,
  // needed for redirect flow
  val reference: String = "Reference",
)

@Serializable
data class ThreeDS(
  val enabled: Boolean = true,
)
