package com.checkout.android.components.sample.ui.model

import com.checkout.components.interfaces.api.PaymentMethodComponent

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
) : MainScreenState()

/**
 * Represents the state where the settings screen is displayed.
 */
object SettingScreenState : MainScreenState()
