package com.checkout.android.components.sample.core.model

import com.checkout.components.interfaces.model.paymentsession.PaymentAction

data class SubmitPaymentSessionResult(
  val id: String = "",
  val type: String = "",
  val status: String = "",
  val declineReason: String? = null,
  val action: PaymentAction? = null,
  val error: String? = null,
)
