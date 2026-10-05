package com.checkout.android.components.sample.core.network.model.session

import com.checkout.android.components.sample.core.model.AddressAndPhoneNumber
import com.checkout.android.components.sample.core.model.Customer
import com.checkout.android.components.sample.core.model.PaymentItem
import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentSessions(
  val amount: Int = 10500,
  val currency: String = "GBP",
  val reference: String = "REFERENCE",
  val billing: AddressAndPhoneNumber? = null,
  @SerialName("success_url")
  val successUrl: String = "https://success_calback",
  @SerialName("failure_url")
  val failureUrl: String = "https://failure_calback",
  val customer: Customer? = null,
  @SerialName("processing_channel_id")
  @EncodeDefault(EncodeDefault.Mode.NEVER)
  val processingChannelId: String? = null,
  val shipping: AddressAndPhoneNumber? = null,
  @SerialName("enabled_payment_methods")
  val enabledPaymentMethods: List<String>,
  @SerialName("payment_method_configuration")
  @EncodeDefault(EncodeDefault.Mode.NEVER)
  val paymentMethodConfiguration: PaymentMethodConfiguration? = null,
  val items: List<PaymentItem> = listOf(PaymentItem("Item 1", 1, 100)),
  @SerialName("3ds")
  val threeDS: ThreeDS = ThreeDS(),
  val locale: String? = null,
)

@Serializable
data class PaymentMethodConfiguration(
  @SerialName("stored_card")
  @EncodeDefault(EncodeDefault.Mode.NEVER)
  val storedCard: StoredCardConfiguration? = null,
)

@Serializable
data class StoredCardConfiguration(
  @SerialName("customer_id")
  val customerId: String,
)

@Serializable
data class ThreeDS(
  val enabled: Boolean = true,
)
