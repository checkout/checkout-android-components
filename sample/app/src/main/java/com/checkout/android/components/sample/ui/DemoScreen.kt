package com.checkout.android.components.sample.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.checkout.android.components.sample.R
import com.checkout.android.components.sample.ui.components.FlowFeedbackBottomSheet
import com.checkout.android.components.sample.ui.components.TermsAndConditionsRow
import com.checkout.android.components.sample.ui.components.UpdateDetailsRow
import com.checkout.android.components.sample.ui.model.AdvancedSettings
import com.checkout.android.components.sample.ui.model.InitialScreenState
import com.checkout.android.components.sample.ui.model.MainScreenState
import com.checkout.android.components.sample.ui.model.PaymentComponentScreenState
import com.checkout.android.components.sample.ui.model.RememberMeSettings
import com.checkout.android.components.sample.ui.model.SettingScreenState
import com.checkout.android.components.sample.ui.model.Settings
import com.checkout.android.components.sample.ui.theme.CheckoutComponentSampleTheme
import com.checkout.components.interfaces.api.PaymentMethodComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoScreen(
  screenState: MainScreenState,
  settingState: Settings,
  advancedSettingsState: AdvancedSettings,
  rememberMeSettings: RememberMeSettings,
  modifier: Modifier = Modifier,
  showSettings: () -> Unit = {},
  showFlowComponent: () -> Unit,
  updateSettings: (Settings) -> Unit,
  updateAdvancedSettings: (AdvancedSettings) -> Unit,
  updateRememberMeSettings: (RememberMeSettings) -> Unit,
  onSubmitClicked: () -> Unit = {},
  onAmountChanged: (Int) -> Unit = {},
  onCheckTermsAndConditions: (Boolean) -> Unit = {},
  onDismissBottomSheet: () -> Unit = {},
) {
  Box {
    Column(
      modifier = modifier,
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      FlowActionHeader(
        modifier = Modifier.fillMaxWidth(),
        onSettingsClicked = showSettings,
        onFlowClicked = showFlowComponent,
      )

      Spacer(modifier = Modifier.height(12.dp))

      when (screenState) {
        is PaymentComponentScreenState -> {
          RenderComponent(
            modifier = Modifier.weight(1f),
            paymentComponent = screenState.paymentComponent,
          )
        }

        is SettingScreenState -> {
          SettingsScreen(
            settings = settingState,
            advancedSettings = advancedSettingsState,
            onUpdated = updateSettings,
            onUpdateAdvancedSettings = updateAdvancedSettings,
            rememberMeSettings = rememberMeSettings,
            onUpdateRememberMeSettings = updateRememberMeSettings,
          )
        }

        else -> {}
      }

      if (!advancedSettingsState.showCardPayButton) {
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(
          onClick = onSubmitClicked,
          modifier = Modifier.fillMaxWidth(),
        ) {
          Text(stringResource(R.string.label_button_submit))
        }
      }

      if (advancedSettingsState.showUpdateAmountView) {
        Spacer(modifier = Modifier.height(12.dp))
        UpdateDetailsRow(onAmountChanged = onAmountChanged)
      }

      Spacer(modifier = Modifier.height(12.dp))

      if (screenState is PaymentComponentScreenState && screenState.showTermsAndConditions) {
        TermsAndConditionsRow(
          checked = screenState.termsAndConditionsAccepted,
          onCheckedChange = onCheckTermsAndConditions,
        )
      }

      if (screenState is PaymentComponentScreenState &&
        (screenState.paymentResult.isSuccess() || screenState.paymentResult.isError())
      ) {
        FlowFeedbackBottomSheet(
          screenState.paymentResult,
          onDismiss = onDismissBottomSheet,
        )
      }
    }
  }
}

@Composable
private fun RenderComponent(
  paymentComponent: PaymentMethodComponent,
  modifier: Modifier = Modifier,
) {
  var isAvailable by remember { mutableStateOf(false) }
  LaunchedEffect(paymentComponent) {
    isAvailable = paymentComponent.isAvailable()
  }

  if (isAvailable) {
    Box(modifier = modifier) {
      paymentComponent.Render()
    }
  }
}

@Composable
fun FlowActionHeader(
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
      onClick = onFlowClicked,
      shape = MaterialTheme.shapes.extraLarge.copy(all = CornerSize(2.dp)),
      colors = ButtonDefaults.buttonColors(
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
      onClick = onSettingsClicked,
    ) {
      Icon(imageVector = Icons.Default.Settings, contentDescription = null)
    }
  }
}

@Preview
@Composable
private fun DemoScreenPreview() {
  CheckoutComponentSampleTheme {
    DemoScreen(
      modifier = Modifier.fillMaxSize(),
      screenState = InitialScreenState,
      settingState = Settings(),
      advancedSettingsState = AdvancedSettings(),
      rememberMeSettings = RememberMeSettings(),
      showSettings = {},
      showFlowComponent = {},
      updateSettings = {},
      updateAdvancedSettings = {},
      updateRememberMeSettings = {},
    )
  }
}
