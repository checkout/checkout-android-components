package com.checkout.android.components.sample.ui.model

import com.checkout.components.interfaces.localisation.Locale

enum class RegionPreset {
  UK,
  EU,
  MENA_AED,
  MENA_SAR,
  APAC,
  ;

  override fun toString(): String = when (this) {
    UK -> "UK"
    EU -> "EU"
    MENA_AED -> "MENA (AED)"
    MENA_SAR -> "MENA (SAR)"
    APAC -> "APAC"
  }

  fun applyTo(settings: Settings): Settings = when (this) {
    UK -> settings.copy(
      preset = UK,
      psCurrency = "GBP",
      psCountry = "GB",
      psEmail = "",
      paymentMethods = listOf(PaymentMethods.Card, PaymentMethods.GooglePay),
      locale = Locale.En,
      psLocale = Locale.En,
    )

    EU -> settings.copy(
      preset = EU,
      psCurrency = "EUR",
      psCountry = "DE",
      psEmail = "",
      paymentMethods = listOf(PaymentMethods.Card, PaymentMethods.GooglePay),
      locale = Locale.De,
      psLocale = Locale.De,
    )

    MENA_AED -> settings.copy(
      preset = MENA_AED,
      psCurrency = "AED",
      psCountry = "AE",
      psEmail = "otp.success@tabby.ai",
      paymentMethods = listOf(PaymentMethods.Card, PaymentMethods.Tabby, PaymentMethods.Tamara),
      locale = Locale.Ar,
      psLocale = Locale.Ar,
    )

    MENA_SAR -> settings.copy(
      preset = MENA_SAR,
      psCurrency = "SAR",
      psCountry = "SA",
      psEmail = "otp.success@tabby.ai",
      paymentMethods = listOf(PaymentMethods.Card, PaymentMethods.Tabby, PaymentMethods.Tamara),
      locale = Locale.Ar,
      psLocale = Locale.Ar,
    )

    APAC -> settings.copy(
      preset = APAC,
      psCurrency = "SGD",
      psCountry = "SG",
      psEmail = "",
      paymentMethods = listOf(PaymentMethods.Card, PaymentMethods.GooglePay),
      locale = Locale.En,
      psLocale = Locale.En,
    )
  }
}
