package com.checkout.android.components.sample.core.model

data class PaymentSessionResult(
  val id: String = "",
  val secret: String = "",
  val error: String? = null,
)
