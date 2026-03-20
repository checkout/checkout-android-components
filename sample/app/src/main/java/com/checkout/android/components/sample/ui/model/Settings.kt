package com.checkout.android.components.sample.ui.model

import androidx.compose.runtime.Stable
import com.checkout.components.interfaces.Environment
import com.checkout.components.interfaces.localisation.Locale

/**
 * Basic Configuration settings
 *
 */
@Stable
data class Settings(
  val component: Components = Components.Flow,
  val paymentMethods: List<PaymentMethods> = listOf(PaymentMethods.Card, PaymentMethods.GooglePay),
  val environment: Environment = Environment.SANDBOX,
  val appearance: Appearance = Appearance.Light,
  val locale: Locale = Locale.En,
  val psLocale: Locale = Locale.En,
)
