package com.checkout.android.components.sample.ui.model

import androidx.compose.runtime.Stable
import com.checkout.components.interfaces.localisation.Locale

@Stable
data class Settings(
  val component: Components = Components.Flow,
  val paymentMethods: List<PaymentMethods> = listOf(PaymentMethods.Card, PaymentMethods.GooglePay),
  val environment: Environment = Environment.Sandbox,
  val appearance: Appearance = Appearance.Light,
  val locale: Locale = Locale.En,
  val psLocale: Locale = Locale.En,
)

enum class Components {
  Flow,
  Card,
  GooglePay,
}

enum class PaymentMethods {
  Card,
  GooglePay,
}

enum class Environment {
  Sandbox,
  Production,
}

enum class Appearance {
  Light,
  Dark,
}

val Localizations = listOf(
  Locale.En,
  Locale.Ar,
  Locale.Da,
  Locale.De,
  Locale.El,
  Locale.Es,
  Locale.Fi,
  Locale.Fil,
  Locale.Fr,
  Locale.Hi,
  Locale.Id,
  Locale.It,
  Locale.Ja,
  Locale.Ms,
  Locale.Nb,
  Locale.Nl,
  Locale.Pt,
  Locale.Sv,
  Locale.Th,
  Locale.Vi,
  Locale.Zh,
  Locale.ZhHk,
  Locale.ZhTw,
)
