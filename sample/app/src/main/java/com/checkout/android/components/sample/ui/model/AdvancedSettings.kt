package com.checkout.android.components.sample.ui.model

import androidx.compose.runtime.Stable
import com.checkout.components.interfaces.component.PaymentButtonAction
import com.checkout.components.interfaces.model.CardSchemeName
import com.checkout.components.interfaces.model.CardTypeName
import com.checkout.components.interfaces.model.CardholderNamePosition

/**
 * Data class representing the configuration for advanced settings in the sample application.
 *
 * @property advancedSettingsExpanded Whether the advanced settings section is expanded in the UI.
 * @property showCardPayButton Whether the card payment button should be displayed.
 * @property paymentAction The [PaymentButtonAction] to be performed (e.g., PAY, AUTHORIZE).
 * @property displayCardHolder The position where the cardholder name field should be displayed.
 * @property cardAcceptedScheme The list of [CardSchemeName]s accepted for standard card payments.
 * @property googlePayAcceptedScheme The list of [CardSchemeName.GooglePay]s accepted for Google Pay.
 * @property rememberMeAcceptedScheme The list of [CardSchemeName]s accepted for "Remember Me" payments.
 * @property cardAcceptedTypes The list of [CardTypeName]s accepted for standard card payments.
 * @property googlePayAcceptedTypes The list of [CardTypeName.GooglePay]s accepted for Google Pay.
 * @property rememberMeAcceptedTypes The list of [CardTypeName]s accepted for "Remember Me" payments.
 * @property submitPayment The [SubmitPaymentHandler] to be used for payment submission.
 * @property showUpdateAmountView Whether to show [com.checkout.android.components.sample.ui.components.UpdateDetailsRow].
 * @property customButtonType The [PaymentButtonAction] to be used for the custom button.
 * @property addressConfiguration The [SampleAddressConfiguration] to be used for address handling.
 * @property showHandleTapConfiguration Whether to show [com.checkout.android.components.sample.ui.components.TermsAndConditionsRow].
 */
@Stable
data class AdvancedSettings(
  val advancedSettingsExpanded: Boolean = false,
  val showCardPayButton: Boolean = true,
  val paymentAction: PaymentButtonAction = PaymentButtonAction.PAYMENT,
  val displayCardHolder: CardholderNamePosition = CardholderNamePosition.TOP,
  val cardAcceptedScheme: List<CardSchemeName> = CardSchemeList,
  val googlePayAcceptedScheme: List<CardSchemeName.GooglePay> = GooglePayCardSchemeList,
  val rememberMeAcceptedScheme: List<CardSchemeName> = CardSchemeList,
  val cardAcceptedTypes: List<CardTypeName> = CardTypesList,
  val googlePayAcceptedTypes: List<CardTypeName.GooglePay> = GooglePayCardTypesList,
  val rememberMeAcceptedTypes: List<CardTypeName> = CardTypesList,
  val submitPayment: SubmitPaymentHandler = SubmitPaymentHandler.SDK,
  val showUpdateAmountView: Boolean = false,
  val customButtonType: PaymentButtonAction = PaymentButtonAction.PAYMENT,
  val addressConfiguration: SampleAddressConfiguration = SampleAddressConfiguration.Empty,
  val showHandleTapConfiguration: Boolean = false,
)
