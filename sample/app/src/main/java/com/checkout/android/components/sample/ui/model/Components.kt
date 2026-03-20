package com.checkout.android.components.sample.ui.model

/**
 * Represents the available payment components in the sample application.
 *
 * @property Flow The complete payment orchestration flow.
 * @property Card The standalone card entry component.
 * @property GooglePay The standalone Google Pay integration.
 */
enum class Components {
  Flow,
  Card,
  GooglePay,
}
