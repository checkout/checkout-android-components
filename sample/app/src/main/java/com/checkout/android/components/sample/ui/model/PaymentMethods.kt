package com.checkout.android.components.sample.ui.model

/**
 * Represents the available payment methods for [Components.Flow].
 *
 * @property Card Standard credit or debit card payments.
 * @property GooglePay Mobile payments through the Google Pay digital wallet.
 * @property Tabby Buy-now-pay-later APM via redirect flow.
 * @property Tamara Buy-now-pay-later APM via redirect flow.
 * @property Ideal iDEAL redirect APM. Requires an EUR + NL payment session to be offered.
 * @property Knet KNET redirect APM. Requires a KWD + KW payment session to be offered.
 */
enum class PaymentMethods {
  Card,
  GooglePay,
  Tabby,
  Tamara,
  Ideal,
  Knet,
}
