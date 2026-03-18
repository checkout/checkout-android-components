package com.checkout.android.components.sample.core.model

import java.math.BigDecimal

data class Item(
  val name: String,
  val quantity: Int = 1,
  val price: BigDecimal,
)
