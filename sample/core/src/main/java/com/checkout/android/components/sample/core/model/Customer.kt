package com.checkout.android.components.sample.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Customer(
  val email: String,
  val name: String,
  val phone: Phone,
)