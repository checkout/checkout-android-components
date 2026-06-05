package com.checkout.android.components.sample.ui.model

import com.checkout.android.components.sample.core.model.Currency
import com.checkout.components.interfaces.Environment
import com.checkout.components.interfaces.component.PaymentButtonAction
import com.checkout.components.interfaces.localisation.Locale
import com.checkout.components.interfaces.model.CardSchemeName
import com.checkout.components.interfaces.model.CardTypeName
import com.checkout.components.interfaces.model.CardholderNamePosition
import com.checkout.components.interfaces.model.contact.Country

val ComponentList = Components.entries.toList()
val PaymentMethodsList = PaymentMethods.entries.toList()

val EnvironmentList = Environment.entries.toList()

val AppearanceList = Appearance.entries.toList()

val PaymentActionList = PaymentButtonAction.entries.toList()

val CardholderPositionList = CardholderNamePosition.entries.toList()

val SubmitPaymentList = SubmitPaymentHandler.entries.toList()

val AddressConfigList = SampleAddressConfiguration.entries

val CardSchemeList = CardSchemeName.entries

val GooglePayCardSchemeList = CardSchemeName.GooglePay.entries
val CardTypesList = CardTypeName.entries

val GooglePayCardTypesList = CardTypeName.GooglePay.entries

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
val CurrencyList = listOf(Currency.GBP, Currency.AED, Currency.SAR)
val CountryList = listOf(Country.UNITED_KINGDOM, Country.UNITED_ARAB_EMIRATES, Country.SAUDI_ARABIA)
