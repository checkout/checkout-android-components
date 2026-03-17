package com.checkout.android.components.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.checkout.android.components.sample.ui.DemoScreen
import com.checkout.android.components.sample.ui.theme.CheckoutComponentSampleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val viewModel by viewModels<MainViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      CheckoutComponentSampleTheme {
        Scaffold(
          modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->

          val state by viewModel.screenState.collectAsStateWithLifecycle()

          val settingState by viewModel.settingState.collectAsStateWithLifecycle()

          val advancedSettingsState by viewModel.advancedSettings.collectAsStateWithLifecycle()
            val rememberMeSettings by viewModel.rememberMeSettings.collectAsStateWithLifecycle()

          val context = LocalContext.current

          DemoScreen(
            modifier = Modifier
              .padding(innerPadding)
              .fillMaxWidth(),
            screenState = state,
            settingState = settingState,
            advancedSettingsState = advancedSettingsState,
              rememberMeSettings = rememberMeSettings,

              showSettings = viewModel::showSettings,
            showFlowComponent = { viewModel.showFlowComponent(context) },
            updateSettings = viewModel::updateSettings,
            updateAdvancedSettings = viewModel::updateAdvancedSettings,
              updateRememberMeSettings = viewModel::updateRememberMeSettings,
              onSubmitClicked = viewModel::onSubmit,
            onAmountChanged = viewModel::onAmountChanged,
            onCheckTermsAndConditions = viewModel::onCheckTermsAndConditions,
            onDismissBottomSheet = viewModel::onDismissBottomSheet,
          )
        }
      }
    }
  }
}
