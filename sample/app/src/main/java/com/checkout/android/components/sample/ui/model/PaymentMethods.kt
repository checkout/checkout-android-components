package com.checkout.android.components.sample.ui.model

/**
 * Represents the available payment methods for [Components.Flow].
 *
 * @property Card Standard credit or debit card payments.
 * @property GooglePay Mobile payments through the Google Pay digital wallet.
 * @property Tabby Buy-now-pay-later APM via redirect flow.
 * @property Tamara Buy-now-pay-later APM via redirect flow.
 */
enum class PaymentMethods {
  Card,
  GooglePay,
  Tabby,
  Tamara,
}
