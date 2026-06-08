package com.checkout.android.components.sample.core.model

import kotlinx.serialization.Serializable

@Serializable
data class AddressAndPhoneNumber(
  val address: Address,
  val phone: Phone? = null,
)
