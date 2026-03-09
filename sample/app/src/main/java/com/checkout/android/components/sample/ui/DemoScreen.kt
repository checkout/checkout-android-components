package com.checkout.android.components.sample.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.checkout.android.components.sample.R
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
  modifier: Modifier = Modifier,
  showSettings: () -> Unit = {},
  showFlowComponent: () -> Unit,
  updateSettings: (Settings) -> Unit,
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    InitialComponent(
      modifier = Modifier.fillMaxWidth(),
      onSettingsClicked = showSettings,
      onFlowClicked = showFlowComponent,
    )

    Spacer(modifier = Modifier.height(12.dp))

    when (screenState) {
      is PaymentComponentScreenState -> {
        screenState.paymentComponent.Render()
      }

      is SettingScreenState -> {
        SettingsScreen(
          settings = settingState,
          onUpdated = updateSettings,
        )
      }

      else -> {}
    }
  }
}

@Composable
fun InitialComponent(
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
        .heightIn(min = 56.dp),
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

@Preview
@Composable
private fun DemoScreenPreview() {
  CheckoutComponentSampleTheme {
    DemoScreen(
      modifier = Modifier.fillMaxSize(),
      screenState = InitialScreenState,
      settingState = Settings(),
      showSettings = {},
      showFlowComponent = {},
      updateSettings = {},
    )
  }
}
