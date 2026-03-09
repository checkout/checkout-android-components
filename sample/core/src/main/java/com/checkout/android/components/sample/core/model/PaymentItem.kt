package com.checkout.android.components.sample.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class PaymentItem(
  val name: String,
  val quantity: Int,
  @SerialName("unit_price")
  val unitPrice: Int,
)

fun Item.toPaymentItem() = PaymentItem(
  name = name,
  quantity = quantity,
  unitPrice = price.formatAmount(),
)

fun BigDecimal.formatAmount(): Int = times(AmountValue).toInt()

internal val AmountValue = BigDecimal(100)
