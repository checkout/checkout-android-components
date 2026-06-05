package com.checkout.android.components.sample.ui.model

import androidx.compose.runtime.Stable
import com.checkout.android.components.sample.core.model.Currency
import com.checkout.components.interfaces.Environment
import com.checkout.components.interfaces.localisation.Locale
import com.checkout.components.interfaces.model.contact.Country

/**
 * Basic Configuration settings
 *
 */
@Stable
data class Settings(
  val component: Components = Components.Flow,
  val paymentMethods: List<PaymentMethods> = listOf(PaymentMethods.Card, PaymentMethods.GooglePay, PaymentMethods.Tamara, PaymentMethods.Tabby),
  val environment: Environment = Environment.SANDBOX,
  val appearance: Appearance = Appearance.Light,
  val locale: Locale = Locale.En,
  val psLocale: Locale = Locale.En,
  val country: Country = Country.UNITED_KINGDOM,
  val currency: Currency = Currency.GBP,
)
