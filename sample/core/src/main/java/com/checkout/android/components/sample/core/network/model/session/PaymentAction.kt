package com.checkout.android.components.sample.core.network.model.session

import kotlinx.serialization.Serializable

@Serializable
data class PaymentAction(
  val type: String,
  val url: String? = null,
)
