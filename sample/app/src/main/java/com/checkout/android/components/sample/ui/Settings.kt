package com.checkout.android.components.sample.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.checkout.android.components.sample.ui.components.MultiSelectMenu
import com.checkout.android.components.sample.ui.components.SingleSelectMenu
import com.checkout.android.components.sample.ui.components.StatefulDropdownRow
import com.checkout.android.components.sample.ui.model.Appearance
import com.checkout.android.components.sample.ui.model.Components
import com.checkout.android.components.sample.ui.model.Environment
import com.checkout.android.components.sample.ui.model.Localizations
import com.checkout.android.components.sample.ui.model.PaymentMethods
import com.checkout.android.components.sample.ui.model.Settings

@Composable
fun SettingsScreen(
  settings: Settings,
  onUpdated: (Settings) -> Unit,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier = modifier
      .padding(horizontal = 24.dp, vertical = 24.dp)
      .fillMaxSize(),
  ) {
    item {
      BasicSettings(settings = settings, onUpdated = onUpdated)
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
        .padding(vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(text = "Component:", modifier = Modifier.width(100.dp), fontSize = 16.sp)

      SingleSelectMenu(
        options = Components.entries.toList(),
        selectedOption = settings.component,
        onOptionSelected = { component ->
          onUpdated(settings.copy(component = component))
        },
      )

      Spacer(modifier = Modifier.width(12.dp))

      if (settings.component == Components.Flow) {
        Text(text = "with", fontSize = 16.sp)

        Spacer(modifier = Modifier.width(12.dp))

        MultiSelectMenu(
          options = PaymentMethods.entries.toList(),
          selectedOptions = settings.paymentMethods.toSet(),
          onOptionToggled = { option ->
            val paymentMethodsSelection = if (settings.paymentMethods.contains(option)) {
              settings.paymentMethods - option
            } else {
              settings.paymentMethods + option
            }

            onUpdated(settings.copy(paymentMethods = paymentMethodsSelection))
          },
        )
      }
    }

    StatefulDropdownRow(
      "Environment:",
      Environment.entries.toList(),
      settings.environment,
    ) { environment -> onUpdated(settings.copy(environment = environment)) }

    StatefulDropdownRow(
      "Appearance:",
      Appearance.entries.toList(),
      settings.appearance,
    ) { onUpdated(settings.copy(appearance = it)) }

    StatefulDropdownRow(
      "Locale:",
      Localizations,
      settings.locale,
    ) { localeSelection -> onUpdated(settings.copy(locale = localeSelection)) }

    StatefulDropdownRow(
      "PS Locale:",
      Localizations,
      settings.psLocale,
    ) { localeSelection -> onUpdated(settings.copy(psLocale = localeSelection)) }
  }
}

@Preview
@Composable
private fun BasicSettingsPreview() {
  BasicSettings(Settings())
}
