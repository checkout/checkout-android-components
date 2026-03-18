package com.checkout.android.components.sample.ui.model

import com.checkout.components.interfaces.Environment
import com.checkout.components.interfaces.component.PaymentButtonAction
import com.checkout.components.interfaces.model.CardSchemeName
import com.checkout.components.interfaces.model.CardTypeName
import com.checkout.components.interfaces.model.CardholderNamePosition

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
