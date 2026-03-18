package com.checkout.android.components.sample.ui.model

import androidx.compose.runtime.Stable

/**
 * Represents the UI state and configuration for the "Remember Me" feature.
 *
 * @property rememberMeSettingsExpanded Indicates if the settings section for "Remember Me" is expanded in the UI.
 * @property enableRememberMe Whether the "Remember Me" functionality is toggled on.
 * @property showRememberMePayButton Whether the specific payment button for "Remember Me" should be displayed.
 * @property email The email address associated with the user's "Remember Me" profile.
 * @property countryCode The country code for the user's phone number.
 * @property phoneNumber The phone number associated with the user's "Remember Me" profile.
 */
@Stable
data class RememberMeSettings(
  val rememberMeSettingsExpanded: Boolean = false,
  val enableRememberMe: Boolean = false,
  val showRememberMePayButton: Boolean = false,
  val email: String = "",
  val countryCode: String = "",
  val phoneNumber: String = "",
)
