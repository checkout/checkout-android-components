package com.checkout.android.components.sample.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Billing(
  val address: Address,
)

@Serializable
data class Address(
  val country: String,
)
