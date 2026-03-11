package com.checkout.android.components.sample.ui.model

import com.checkout.components.interfaces.api.PaymentMethodComponent
import com.checkout.components.interfaces.model.TokenizationResult

/**
 * Represents the different UI states for the Main Screen in the sample application.
 */
sealed class MainScreenState

/**
 * Represents the initial state of the Main Screen before any interactions
 */
object InitialScreenState : MainScreenState()

/**
 * Represents the state of the screen when a specific payment method component is being displayed.
 */
data class PaymentComponentScreenState(
  val paymentComponent: PaymentMethodComponent,
  val tokenizedResult: TokenizationResult? = null,
  val paymentId: String = "",
  val showTermsAndConditions: Boolean = true,
  val termAndConditions: Boolean = true,
  val addressConfiguration: SampleAddressConfiguration = SampleAddressConfiguration.Empty,
  val showAddressPrefill: Boolean = true,
  val prefillAddress: Boolean = true,
) : MainScreenState()

/**
 * Represents the state where the settings screen is displayed.
 */
object SettingScreenState : MainScreenState()
