package com.checkout.android.components.sample.ui.model

import androidx.compose.runtime.Stable
import com.checkout.components.interfaces.component.PaymentButtonAction
import com.checkout.components.interfaces.localisation.Locale
import com.checkout.components.interfaces.model.CardSchemeName
import com.checkout.components.interfaces.model.CardTypeName
import com.checkout.components.interfaces.model.CardholderNamePosition

@Stable
data class Settings(
  val component: Components = Components.Flow,
  val paymentMethods: List<PaymentMethods> = listOf(
    PaymentMethods.Card,
    PaymentMethods.GooglePay,
  ),
  val environment: Environment = Environment.Sandbox,
  val appearance: Appearance = Appearance.Light,
  val locale: Locale = Locale.En,
  val psLocale: Locale = Locale.En,
)

@Stable
data class AdvancedSettings(
  val advancedSettingsExpanded: Boolean = false,
  val showCardPayButton: Boolean = true,
  val paymentAction: PaymentButtonAction = PaymentButtonAction.PAYMENT,
  val showGooglePayButton: Boolean = true,
  val displayCardHolder: CardholderNamePosition = CardholderNamePosition.TOP,
  val cardAcceptedScheme: List<CardSchemeName> = CARD_SCHEME_ENTRIES,
  val googlePayAcceptedScheme: List<CardSchemeName.GooglePay> = GOOGLE_PAY_CARD_SCHEME_ENTRIES,
  val rememberMeAcceptedScheme: List<CardSchemeName> = CARD_SCHEME_ENTRIES,
  val cardAcceptedTypes: List<CardTypeName> = CARD_TYPE_ENTRIES,
  val googlePayAcceptedTypes: List<CardTypeName.GooglePay> = GOOGLE_PAY_CARD_TYPES_LIST,
  val rememberMeAcceptedTypes: List<CardTypeName> = CARD_TYPE_ENTRIES,
  val submitPayment: SubmitPaymentHandler = SubmitPaymentHandler.SDK,
  val showUpdateAmountView: Boolean = false,
  val customButtonType: PaymentButtonAction = PaymentButtonAction.PAYMENT,
  val addressConfiguration: SampleAddressConfiguration = SampleAddressConfiguration.Empty,
  val showHandleTapConfiguration: Boolean = false,
)

@Stable
data class RememberMeSettings(
  val rememberMeSettingsExpanded: Boolean = false,
  val enableRememberMe: Boolean = false,
  val showRememberMePayButton: Boolean = false,
  val email: String = "",
  val countryCode: String = "",
  val phoneNumber: String = "",
)

val COMPONENTS_LIST = Components.entries.toList()

val PAYMENT_METHODS_LIST = PaymentMethods.entries.toList()

val ENVIRONMENT_LIST = Environment.entries.toList()

val APPEARANCE_LIST = Appearance.entries.toList()

val PAYMENT_ACTION_LIST = PaymentButtonAction.entries.toList()

val CARDHOLDER_POSITION_LIST = CardholderNamePosition.entries.toList()

val SUBMIT_PAYMENT_LIST = SubmitPaymentHandler.entries.toList()

val ADDRESS_CONFIG_LIST = SampleAddressConfiguration.entries

val CARD_SCHEME_ENTRIES = CardSchemeName.entries

val GOOGLE_PAY_CARD_SCHEME_ENTRIES = CardSchemeName.GooglePay.entries
val CARD_TYPE_ENTRIES = CardTypeName.entries

val GOOGLE_PAY_CARD_TYPES_LIST = CardTypeName.GooglePay.entries

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

enum class SubmitPaymentHandler {
  SDK,
  HandleSubmit,
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

fun <T> List<T>.addOrRemove(item: T): List<T> {
  if (contains(item)) {
    return this - item
  } else {
    return this + item
  }
}

fun CardTypeName.displayName(): String = this::class.java.simpleName
