package com.checkout.android.components.sample.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentItem(
  val name: String,
  val quantity: Int,
  @SerialName("unit_price")
  val unitPrice: Int,
)
