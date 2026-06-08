package com.checkout.android.components.sample.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Phone(
  val number: String,
  @SerialName("country_code")
  val countryCode: String,
)