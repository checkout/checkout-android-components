package com.checkout.android.components.sample

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkout.android.components.sample.factory.FlowComponent
import com.checkout.android.components.sample.ui.model.Components
import com.checkout.android.components.sample.ui.model.InitialScreenState
import com.checkout.android.components.sample.ui.model.MainScreenState
import com.checkout.android.components.sample.ui.model.PaymentComponentScreenState
import com.checkout.android.components.sample.ui.model.SettingScreenState
import com.checkout.android.components.sample.ui.model.Settings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val flowComponent: FlowComponent,
) : ViewModel() {

  private val _screenState = MutableStateFlow<MainScreenState>(InitialScreenState)
  private val _settingState = MutableStateFlow(Settings())

  val screenState = _screenState.asStateFlow()

  val settingState = _settingState.asStateFlow()

  fun showFlowComponent(context: Context) {
    viewModelScope.launch {
      val config = flowComponent.createConfigurationFromSettings(
        context = context,
        settings = settingState.value,
      )

      val component = flowComponent.createComponents(config)

      val paymentMethodComponent = when (settingState.value.component) {
        Components.Flow -> flowComponent.createFlowPaymentMethodComponent(component)
        Components.Card -> flowComponent.createCardPaymentMethodComponent(component)
        Components.GooglePay -> flowComponent.createFlowPaymentMethodComponent(component)
      }

      _screenState.update { PaymentComponentScreenState(paymentMethodComponent) }
    }
  }

  fun showSettings() {
    _screenState.update { SettingScreenState }
  }

  fun updateSettings(settings: Settings) {
    _settingState.update { settings }
  }

  override fun onCleared() {
    super.onCleared()
    flowComponent.onCleared()
  }
}
