@file:Suppress("RestrictedApi")

package com.checkout.android.components.sample.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.checkout.android.components.sample.R
import com.checkout.android.components.sample.ui.components.HEADER_VERTICAL_PADDING
import com.checkout.android.components.sample.ui.components.HORIZONTAL_ARRANGEMENT
import com.checkout.android.components.sample.ui.components.LAZY_COLUMN_PADDING
import com.checkout.android.components.sample.ui.components.MultiSelectMenu
import com.checkout.android.components.sample.ui.components.MultiSelectionList
import com.checkout.android.components.sample.ui.components.PrimaryExpandableRow
import com.checkout.android.components.sample.ui.components.ROW_VERTICAL_PADDING
import com.checkout.android.components.sample.ui.components.SampleEmailOutlinedTextField
import com.checkout.android.components.sample.ui.components.SampleNumberOutlinedTextField
import com.checkout.android.components.sample.ui.components.SecondaryDropdownRow
import com.checkout.android.components.sample.ui.components.SecondaryExpandableRow
import com.checkout.android.components.sample.ui.components.SingleSelectMenu
import com.checkout.android.components.sample.ui.components.StatefulDropdownRow
import com.checkout.android.components.sample.ui.components.SwitchRow
import com.checkout.android.components.sample.ui.model.ADDRESS_CONFIG_LIST
import com.checkout.android.components.sample.ui.model.APPEARANCE_LIST
import com.checkout.android.components.sample.ui.model.AdvancedSettings
import com.checkout.android.components.sample.ui.model.CARDHOLDER_POSITION_LIST
import com.checkout.android.components.sample.ui.model.CARD_SCHEME_ENTRIES
import com.checkout.android.components.sample.ui.model.CARD_TYPE_ENTRIES
import com.checkout.android.components.sample.ui.model.COMPONENTS_LIST
import com.checkout.android.components.sample.ui.model.Components
import com.checkout.android.components.sample.ui.model.ENVIRONMENT_LIST
import com.checkout.android.components.sample.ui.model.GOOGLE_PAY_CARD_SCHEME_ENTRIES
import com.checkout.android.components.sample.ui.model.GOOGLE_PAY_CARD_TYPES_LIST
import com.checkout.android.components.sample.ui.model.Localizations
import com.checkout.android.components.sample.ui.model.PAYMENT_ACTION_LIST
import com.checkout.android.components.sample.ui.model.PAYMENT_METHODS_LIST
import com.checkout.android.components.sample.ui.model.RememberMeSettings
import com.checkout.android.components.sample.ui.model.SUBMIT_PAYMENT_LIST
import com.checkout.android.components.sample.ui.model.Settings
import com.checkout.android.components.sample.ui.model.SubmitPaymentHandler
import com.checkout.android.components.sample.ui.model.addOrRemove
import com.checkout.android.components.sample.ui.model.displayName
import com.checkout.android.components.sample.ui.theme.CheckoutComponentSampleTheme
import com.checkout.components.interfaces.model.CardSchemeName.Companion.displayName

@Composable
fun SettingsScreen(
  settings: Settings,
  advancedSettings: AdvancedSettings,
  rememberMeSettings: RememberMeSettings,
  onUpdated: (Settings) -> Unit,
  onUpdateAdvancedSettings: (AdvancedSettings) -> Unit,
  onUpdateRememberMeSettings: (RememberMeSettings) -> Unit,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier = modifier
      .padding(horizontal = LAZY_COLUMN_PADDING, vertical = LAZY_COLUMN_PADDING)
      .fillMaxSize(),
  ) {
    item {
      BasicSettings(settings = settings, onUpdated = onUpdated)
    }

    item {
      AdvancedSettings(
        advancedSettings = advancedSettings,
        onUpdated = onUpdateAdvancedSettings,
      )
    }

    item {
      RememberMeSettings(
        rememberMeSettings = rememberMeSettings,
        onUpdated = onUpdateRememberMeSettings,
      )
    }
  }
}

@Composable
fun BasicSettings(
  settings: Settings,
  modifier: Modifier = Modifier,
  onUpdated: (Settings) -> Unit = {},
) {
  Column(
    modifier = modifier,
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = ROW_VERTICAL_PADDING),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(HORIZONTAL_ARRANGEMENT),
    ) {
      Text(
        text = stringResource(R.string.label_component),
        modifier = Modifier,
        style = MaterialTheme.typography.bodyLarge,
      )

      SingleSelectMenu(
        options = COMPONENTS_LIST,
        selectedOption = settings.component,
        onOptionSelected = { component ->
          onUpdated(settings.copy(component = component))
        },
      )

      if (settings.component == Components.Flow) {
        Text(
          text = stringResource(R.string.label_with),
          style = MaterialTheme.typography.bodyLarge,
        )

        MultiSelectMenu(
          options = PAYMENT_METHODS_LIST,
          selectedOptions = settings.paymentMethods.toSet(),
          onOptionToggled = { option ->
            val paymentMethodsSelection = settings.paymentMethods.addOrRemove(option)

            onUpdated(settings.copy(paymentMethods = paymentMethodsSelection))
          },
        )
      }
    }

    StatefulDropdownRow(
      stringResource(R.string.label_environment),
      ENVIRONMENT_LIST,
      settings.environment,
    ) { environment -> onUpdated(settings.copy(environment = environment)) }

    StatefulDropdownRow(
      stringResource(R.string.label_appearance),
      APPEARANCE_LIST,
      settings.appearance,
    ) { onUpdated(settings.copy(appearance = it)) }

    StatefulDropdownRow(
      stringResource(R.string.label_locale),
      Localizations,
      settings.locale,
    ) { localeSelection -> onUpdated(settings.copy(locale = localeSelection)) }

    StatefulDropdownRow(
      stringResource(R.string.label_ps_locale),
      Localizations,
      settings.psLocale,
    ) { localeSelection -> onUpdated(settings.copy(psLocale = localeSelection)) }
  }
}

@Composable
fun AdvancedSettings(
  advancedSettings: AdvancedSettings,
  onUpdated: (AdvancedSettings) -> Unit,
) {
  PrimaryExpandableRow(
    label = stringResource(R.string.label_advanced_features),
    isExpanded = advancedSettings.advancedSettingsExpanded,
    onExpanded = { onUpdated(advancedSettings.copy(advancedSettingsExpanded = it)) },
  ) {
    Column(modifier = Modifier.padding(start = HEADER_VERTICAL_PADDING)) {
      SwitchRow(
        stringResource(R.string.label_show_card_pay_button),
        advancedSettings.showCardPayButton,
      ) { onUpdated(advancedSettings.copy(showCardPayButton = it)) }

      SecondaryDropdownRow(
        stringResource(R.string.label_payment_button_action),
        PAYMENT_ACTION_LIST,
        advancedSettings.paymentAction,
      ) { onUpdated(advancedSettings.copy(paymentAction = it)) }

      SwitchRow(
        stringResource(R.string.label_show_google_pay_button),
        advancedSettings.showGooglePayButton,
      ) { onUpdated(advancedSettings.copy(showGooglePayButton = it)) }

      SecondaryDropdownRow(
        stringResource(R.string.label_display_cardholder_name),
        CARDHOLDER_POSITION_LIST,
        advancedSettings.displayCardHolder,
      ) { onUpdated(advancedSettings.copy(displayCardHolder = it)) }

      SecondaryExpandableRow(
        label = stringResource(R.string.label_card_accepted_schemes),
      ) {
        MultiSelectionList(
          options = CARD_SCHEME_ENTRIES,
          selectedOptions = advancedSettings.cardAcceptedScheme,
          displayText = { it.displayName() },
        ) { selectedScheme ->
          val cardSchemes =
            advancedSettings.cardAcceptedScheme.addOrRemove(selectedScheme)
          onUpdated(advancedSettings.copy(cardAcceptedScheme = cardSchemes))
        }
      }

      SecondaryExpandableRow(
        label = stringResource(R.string.label_google_pay_accepted_schemes),
      ) {
        MultiSelectionList(
          options = GOOGLE_PAY_CARD_SCHEME_ENTRIES,
          selectedOptions = advancedSettings.googlePayAcceptedScheme,
          displayText = { it.displayName() },
        ) { selectedScheme ->
          val cardSchemes =
            advancedSettings.googlePayAcceptedScheme.addOrRemove(selectedScheme)
          onUpdated(advancedSettings.copy(googlePayAcceptedScheme = cardSchemes))
        }
      }

      SecondaryExpandableRow(
        label = stringResource(R.string.label_remember_me_accepted_schemes),
      ) {
        MultiSelectionList(
          options = CARD_SCHEME_ENTRIES,
          selectedOptions = advancedSettings.rememberMeAcceptedScheme,
          displayText = { it.displayName() },
        ) { selectedScheme ->
          val cardSchemes =
            advancedSettings.rememberMeAcceptedScheme.addOrRemove(selectedScheme)
          onUpdated(advancedSettings.copy(rememberMeAcceptedScheme = cardSchemes))
        }
      }

      SecondaryExpandableRow(
        label = stringResource(R.string.label_card_accepted_types),
      ) {
        MultiSelectionList(
          options = CARD_TYPE_ENTRIES,
          selectedOptions = advancedSettings.cardAcceptedTypes,
          displayText = { it.displayName() },
        ) { selectedScheme ->
          val cardSchemes = advancedSettings.cardAcceptedTypes.addOrRemove(selectedScheme)
          onUpdated(advancedSettings.copy(cardAcceptedTypes = cardSchemes))
        }
      }

      SecondaryExpandableRow(
        label = stringResource(R.string.label_google_pay_accepted_types),
      ) {
        MultiSelectionList(
          options = GOOGLE_PAY_CARD_TYPES_LIST,
          selectedOptions = advancedSettings.googlePayAcceptedTypes,
          displayText = { it.displayName() },
        ) { selectedScheme ->
          val cardSchemes =
            advancedSettings.googlePayAcceptedTypes.addOrRemove(selectedScheme)
          onUpdated(advancedSettings.copy(googlePayAcceptedTypes = cardSchemes))
        }
      }

      SecondaryExpandableRow(
        label = stringResource(R.string.label_remember_me_accepted_types),
      ) {
        MultiSelectionList(
          options = CARD_TYPE_ENTRIES,
          selectedOptions = advancedSettings.rememberMeAcceptedTypes,
          displayText = { it.displayName() },
        ) { selectedScheme ->
          val cardSchemes =
            advancedSettings.rememberMeAcceptedTypes.addOrRemove(selectedScheme)
          onUpdated(advancedSettings.copy(rememberMeAcceptedTypes = cardSchemes))
        }
      }

      SecondaryDropdownRow(
        stringResource(R.string.label_submit_payment),
        SUBMIT_PAYMENT_LIST,
        advancedSettings.submitPayment,
      ) { handler ->
        val showAmount =
          if (handler == SubmitPaymentHandler.SDK) false else advancedSettings.showUpdateAmountView
        onUpdated(
          advancedSettings.copy(
            submitPayment = handler,
            showUpdateAmountView = showAmount,
          ),
        )
      }

      if (advancedSettings.submitPayment == SubmitPaymentHandler.HandleSubmit) {
        SwitchRow(
          stringResource(R.string.label_show_update_amount_view),
          advancedSettings.showUpdateAmountView,
        ) { onUpdated(advancedSettings.copy(showUpdateAmountView = it)) }
      }

      SecondaryDropdownRow(
        stringResource(R.string.label_custom_button_type),
        PAYMENT_ACTION_LIST,
        advancedSettings.customButtonType,
      ) { onUpdated(advancedSettings.copy(customButtonType = it)) }

      SecondaryDropdownRow(
        stringResource(R.string.label_address_config),
        ADDRESS_CONFIG_LIST,
        advancedSettings.addressConfiguration,
        displayText = { it.displayName() },
        selectedDisplayText = { it.displayName() },
      ) { onUpdated(advancedSettings.copy(addressConfiguration = it)) }

      SwitchRow(
        stringResource(R.string.label_show_handle_tap),
        advancedSettings.showHandleTapConfiguration,
      ) { onUpdated(advancedSettings.copy(showHandleTapConfiguration = it)) }
    }
  }
}

@Composable
fun RememberMeSettings(
  rememberMeSettings: RememberMeSettings,
  modifier: Modifier = Modifier,
  onUpdated: (RememberMeSettings) -> Unit = {},
) {
  PrimaryExpandableRow(
    label = stringResource(R.string.label_rememberme_configuration),
    isExpanded = rememberMeSettings.rememberMeSettingsExpanded,
    onExpanded = { onUpdated(rememberMeSettings.copy(rememberMeSettingsExpanded = it)) },
  ) {
    Column(
      modifier = modifier.padding(start = HEADER_VERTICAL_PADDING),
      verticalArrangement = Arrangement.spacedBy(ROW_VERTICAL_PADDING),
    ) {
      SwitchRow(
        stringResource(R.string.label_rememberme_enable),
        rememberMeSettings.enableRememberMe,
      ) { onUpdated(rememberMeSettings.copy(enableRememberMe = it)) }

      AnimatedVisibility(
        visible = rememberMeSettings.enableRememberMe,
      ) {
        Column(
          verticalArrangement = Arrangement.spacedBy(ROW_VERTICAL_PADDING),
        ) {
          SwitchRow(
            stringResource(R.string.label_rememberme_show_pay_button),
            rememberMeSettings.showRememberMePayButton,
          ) { onUpdated(rememberMeSettings.copy(showRememberMePayButton = it)) }

          SampleEmailOutlinedTextField(
            label = stringResource(R.string.label_rememberme_email),
            currentValue = rememberMeSettings.email,
            onDone = {
              onUpdated(rememberMeSettings.copy(email = it))
            },
          )
          SampleNumberOutlinedTextField(
            label = stringResource(R.string.label_rememberme_country_code),
            currentValue = rememberMeSettings.countryCode,
            onDone = {
              onUpdated(rememberMeSettings.copy(countryCode = it))
            },
          )
          SampleNumberOutlinedTextField(
            label = stringResource(R.string.label_rememberme_phone_number),
            currentValue = rememberMeSettings.phoneNumber,
            onDone = {
              onUpdated(rememberMeSettings.copy(phoneNumber = it))
            },
          )
        }
      }
    }
  }
}

@Preview
@Composable
private fun BasicSettingsPreview() {
  CheckoutComponentSampleTheme {
    BasicSettings(Settings())
  }
}

@Preview
@Composable
private fun AdvancedSettingsPreview() {
  CheckoutComponentSampleTheme {
    AdvancedSettings(
      advancedSettings = AdvancedSettings(),
      onUpdated = {},
    )
  }
}

@Preview
@Composable
private fun RememberMeSettingsPreview() {
  CheckoutComponentSampleTheme {
    RememberMeSettings(rememberMeSettings = RememberMeSettings())
  }
}
