package com.checkout.android.components.sample.ui.model

import androidx.compose.runtime.Stable
import com.checkout.components.interfaces.api.PaymentMethodComponent
import com.checkout.components.interfaces.error.CheckoutError

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
@Stable
data class PaymentComponentScreenState(
  val paymentComponent: PaymentMethodComponent,
  val showTermsAndConditions: Boolean = true,
  val termsAndConditionsAccepted: Boolean = true,
  val addressConfiguration: SampleAddressConfiguration = SampleAddressConfiguration.Empty,
  val showAddressPrefill: Boolean = true,
  val prefillAddress: Boolean = true,
  val paymentResult: PaymentResultState = PaymentResultState(),
) : MainScreenState()

/**
 * Represents the state where the settings screen is displayed.
 */
object SettingScreenState : MainScreenState()

@Stable
data class PaymentResultState(
  val paymentId: String = "",
  val token: String = "",
  val error: CheckoutError? = null,
) {

  fun isSuccess(): Boolean = paymentId.isNotEmpty() && token.isNotEmpty()

  fun isError(): Boolean = error != null
}
