package com.checkout.android.components.sample.ui.model

data class PaymentSessionConfiguration(
  val paymentSessionConfigurationExpanded: Boolean = false,
  val customerName: String = "",
  val customerEmail: String = "",
  val countryCode: String = "",
  val phoneNumber: String = "",
)
