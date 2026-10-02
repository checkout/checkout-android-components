package com.checkout.android.components.sample.ui.model

import androidx.compose.runtime.Stable
import com.checkout.components.interfaces.model.CardSchemeName
import com.checkout.components.interfaces.model.CardTypeName
import com.checkout.components.interfaces.model.StoredCardDisplayMode

/**
 * Represents the UI state and configuration for the Merchant Saved Card (stored card) feature,
 * which blends with "Remember Me".
 *
 * @property storedCardSettingsExpanded Whether the stored card settings section is expanded in the UI.
 * @property customerId The stored card customer reference sent on the session
 * (`payment_method_configuration.stored_card.customer_id`); the merchant's saved cards are resolved from it.
 * A blank value gates the whole feature off, so the session and component stay untouched.
 * @property displayMode The [StoredCardDisplayMode] passed to the component (DEFAULT_ONLY or ALL).
 * @property captureCardCvv Whether the component should capture the CVV for stored card payments.
 * @property acceptedCardSchemes The list of [CardSchemeName]s accepted for stored card payments.
 * @property acceptedCardTypes The list of [CardTypeName]s accepted for stored card payments.
 */
@Stable
data class StoredCardSettings(
  val storedCardSettingsExpanded: Boolean = false,
  val customerId: String = "",
  val displayMode: StoredCardDisplayMode = StoredCardDisplayMode.DEFAULT_ONLY,
  val captureCardCvv: Boolean = false,
  val acceptedCardSchemes: List<CardSchemeName> = CardSchemeList,
  val acceptedCardTypes: List<CardTypeName> = CardTypesList,
) {
  /**
   * Stored cards are enabled when a customer reference is present. The customer id is what the
   * session needs to resolve saved cards, so its presence is the single source of truth for the
   * feature being on.
   */
  val enabled: Boolean
    get() = customerId.isNotBlank()
}
