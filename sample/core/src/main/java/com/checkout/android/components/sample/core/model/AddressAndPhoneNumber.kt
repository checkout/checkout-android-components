package com.checkout.android.components.sample.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressAndPhoneNumber(
  val address: Address,
  val phone: Phone? = null,
)

@Serializable
data class Address(
  val country: String,
  @SerialName("address_line1")
  val addressLine1: String? = null,
  @SerialName("address_line2")
  val addressLine2: String? = null,
  val city: String? = null,
  val state: String? = null,
  val zip: String? = null,
)

@Serializable
data class Phone(
  val number: String,
  @SerialName("country_code")
  val countryCode: String,
)

@Serializable
data class Customer(
  val email: String,
  val name: String,
  val phone: Phone,
)
