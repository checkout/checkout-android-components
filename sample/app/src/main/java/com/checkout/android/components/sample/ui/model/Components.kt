package com.checkout.android.components.sample.ui.model

/**
 * Represents the available payment components in the sample application.
 *
 * @property Flow The complete payment orchestration flow.
 * @property Card The standalone card entry component.
 * @property GooglePay The standalone Google Pay integration.
 * @property Tabby Standalone Tabby buy-now-pay-later APM.
 * @property Tamara Standalone Tamara buy-now-pay-later APM.
 */
enum class Components {
  Flow,
  Card,
  GooglePay,
  Tabby,
  Tamara,
}
