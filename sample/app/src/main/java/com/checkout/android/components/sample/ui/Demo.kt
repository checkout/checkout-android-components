package com.checkout.android.components.sample.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.checkout.android.components.sample.R
import com.checkout.android.components.sample.ui.model.AdvancedSettings
import com.checkout.android.components.sample.ui.model.InitialScreenState
import com.checkout.android.components.sample.ui.model.MainScreenState
import com.checkout.android.components.sample.ui.model.PaymentComponentScreenState
import com.checkout.android.components.sample.ui.model.SettingScreenState
import com.checkout.android.components.sample.ui.model.Settings
import com.checkout.android.components.sample.ui.theme.CheckoutComponentSampleTheme

@Composable
fun DemoScreen(
  screenState: MainScreenState,
  settingState: Settings,
  advancedSettingsState: AdvancedSettings,
  modifier: Modifier = Modifier,
  showSettings: () -> Unit = {},
  showFlowComponent: () -> Unit,
  updateSettings: (Settings) -> Unit,
  updateAdvancedSettings: (AdvancedSettings) -> Unit,
  onSubmitClicked: () -> Unit = {},
  onAmountChanged: (Int) -> Unit = {},
  onCheckTermsAndConditions: (Boolean) -> Unit = {},
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    InitialScreen(
      modifier = Modifier.fillMaxWidth(),
      onSettingsClicked = showSettings,
      onFlowClicked = showFlowComponent,
    )

    Spacer(modifier = Modifier.height(12.dp))

    when (screenState) {
      is PaymentComponentScreenState -> {
        Box(modifier = Modifier.weight(1f)) {
          screenState.paymentComponent.Render()
        }
      }

      is SettingScreenState -> {
        SettingsScreen(
          settings = settingState,
          advancedSettings = advancedSettingsState,
          onUpdated = updateSettings,
          onUpdateAdvancedSettings = updateAdvancedSettings,
        )
      }

      else -> {}
    }

    if (!advancedSettingsState.showCardPayButton || !advancedSettingsState.showGooglePayButton) {
      Spacer(modifier = Modifier.height(12.dp))
      TextButton(
        onClick = onSubmitClicked,
        modifier = Modifier.fillMaxWidth(),
      ) {
        Text("Submit")
      }
    }

    if (advancedSettingsState.showUpdateAmountView) {
      Spacer(modifier = Modifier.height(12.dp))
      AmountPanel(onAmountChanged = onAmountChanged)
    }

    Spacer(modifier = Modifier.height(12.dp))

    if (screenState is PaymentComponentScreenState && screenState.showTermsAndConditions) {
      ValidatePayment(
        checked = screenState.termAndConditions,
        onCheckedChange = onCheckTermsAndConditions,
      )
    }
  }
}

@Preview
@Composable
private fun InitialScreenPreview() {
  CheckoutComponentSampleTheme {
    DemoScreen(
      modifier = Modifier.fillMaxSize(),
      screenState = InitialScreenState,
      settingState = Settings(),
      advancedSettingsState = AdvancedSettings(),
      showSettings = {},
      showFlowComponent = {},
      updateSettings = {},
      updateAdvancedSettings = {},
    )
  }
}

@Composable
fun InitialScreen(
  modifier: Modifier = Modifier,
  onSettingsClicked: () -> Unit = {},
  onFlowClicked: () -> Unit = {},
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center,
  ) {
    Button(
      modifier = Modifier
        .heightIn(min = 36.dp),
      onClick = onFlowClicked,
      shape = MaterialTheme.shapes.extraLarge.copy(all = CornerSize(2.dp)),
      colors = ButtonColors(
        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
        contentColor = Color.White.copy(alpha = 0.9f),
        disabledContentColor = Color.White.copy(alpha = 0.6f),
        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f),
      ),
    ) {
      Text(
        text = stringResource(R.string.show_flow),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium,
      )
    }

    IconButton(
      modifier = Modifier
        .heightIn(min = 56.dp),
      onClick = onSettingsClicked,
    ) {
      Icon(imageVector = Icons.Default.Settings, contentDescription = null)
    }
  }
}

@Composable
fun AmountPanel(
  modifier: Modifier = Modifier,
  state: TextFieldState = rememberTextFieldState(),
  onAmountChanged: (Int) -> Unit = {},
) {
  Row(
    modifier = modifier
      .padding(horizontal = 8.dp)
      .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    val focusManager = LocalFocusManager.current

    val updateAmount = {
      runCatching {
        state.text.toString().toInt()
      }.onSuccess {
        onAmountChanged(it)
        state.clearText()
      }.onFailure {
        state.clearText()
      }
    }

    OutlinedTextField(
      modifier = Modifier.weight(1f),
      state = state,
      label = { Text("$") },
      lineLimits = TextFieldLineLimits.SingleLine,
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
      onKeyboardAction = {
        updateAmount()
        focusManager.clearFocus()
      },
    )

    OutlinedButton(
      modifier = Modifier,
      onClick = {
        updateAmount()
        focusManager.clearFocus()
      },
    ) {
      Text("Update Amount")
    }
  }
}

@Composable
fun ValidatePayment(
  modifier: Modifier = Modifier,
  checked: Boolean = true,
  onCheckedChange: (Boolean) -> Unit = {},
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.Start,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Checkbox(
      checked = checked,
      onCheckedChange = { isChecked ->
        onCheckedChange(isChecked)
      },
    )
    Text(
      text = stringResource(R.string.term_condition_text),
      color = if (!checked) Color.Red else Color.Black,
    )
  }
}
