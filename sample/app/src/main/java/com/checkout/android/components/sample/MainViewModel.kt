package com.checkout.android.components.sample

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.checkout.android.components.sample.factory.FlowComponent
import com.checkout.android.components.sample.ui.model.AdvancedSettings
import com.checkout.android.components.sample.ui.model.Components
import com.checkout.android.components.sample.ui.model.InitialScreenState
import com.checkout.android.components.sample.ui.model.MainScreenState
import com.checkout.android.components.sample.ui.model.PaymentComponentScreenState
import com.checkout.android.components.sample.ui.model.SettingScreenState
import com.checkout.android.components.sample.ui.model.Settings
import com.checkout.android.components.sample.ui.model.SubmitPaymentHandler
import com.checkout.components.interfaces.api.PaymentMethodComponent
import com.checkout.components.interfaces.component.CardConfiguration
import com.checkout.components.interfaces.component.ComponentCallback
import com.checkout.components.interfaces.component.ComponentOption
import com.checkout.components.interfaces.component.GooglePayConfiguration
import com.checkout.components.interfaces.model.ApiCallResult
import com.checkout.components.interfaces.model.CallbackResult
import com.checkout.components.interfaces.model.TokenizationResult
import com.checkout.components.interfaces.model.UpdateDetails
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

  private val _advancedSettings = MutableStateFlow(AdvancedSettings())

  val screenState = _screenState.asStateFlow()

  val settingState = _settingState.asStateFlow()

  val advancedSettings = _advancedSettings.asStateFlow()

  fun showFlowComponent(context: Context) {
    viewModelScope.launch {
      val advancedSettings = advancedSettings.value
      val config = flowComponent.createConfigurationFromSettings(
        context = context,
        settings = settingState.value,
        callbacks = buildComponentCallbacks(),
      )

      val component = flowComponent.createComponents(config)

      val specificOptions = ComponentOption(
        showPayButton = advancedSettings.showCardPayButton,
        googlePayConfiguration = GooglePayConfiguration(
          acceptedCardTypes = advancedSettings.googlePayAcceptedTypes,
          acceptedCardSchemes = advancedSettings.googlePayAcceptedScheme,
        ),
        cardConfiguration = CardConfiguration(
          displayCardholderName = advancedSettings.displayCardHolder,
          acceptedCardTypes = advancedSettings.cardAcceptedTypes,
          acceptedCardSchemes = advancedSettings.cardAcceptedScheme,
        ),
        addressConfiguration = advancedSettings.addressConfiguration.configuration(),
      )

      val paymentMethodComponent = when (settingState.value.component) {
        Components.Flow -> flowComponent.createFlowPaymentMethodComponent(
          component,
          specificOptions,
        )

        Components.Card -> flowComponent.createCardPaymentMethodComponent(
          component,
          specificOptions,
        )

        Components.GooglePay -> flowComponent.createFlowPaymentMethodComponent(
          component,
          specificOptions,
        )
      }

      _screenState.update {
        PaymentComponentScreenState(
          paymentComponent = paymentMethodComponent,
          showTermsAndConditions = advancedSettings.showHandleTapConfiguration,
        )
      }
    }
  }

  fun showSettings() {
    _screenState.update { SettingScreenState }
  }

  fun updateSettings(settings: Settings) {
    _settingState.update { settings }
  }

  fun updateAdvancedSettings(advancedSettings: AdvancedSettings) {
    _advancedSettings.update {
      advancedSettings
    }
  }

  fun onSubmit() {
    viewModelScope.launch {
      (screenState.value as? PaymentComponentScreenState)?.paymentComponent?.submit()
    }
  }

  fun onAmountChanged(amount: Int) {
    viewModelScope.launch {
      (screenState.value as? PaymentComponentScreenState)?.paymentComponent?.update(
        UpdateDetails(amount = amount),
      )
    }
  }

  fun onCheckTermsAndConditions(value: Boolean) {
    (screenState.value as? PaymentComponentScreenState)?.run {
      _screenState.update { this.copy(termAndConditions = value) }
    }
  }

  private fun buildComponentCallbacks(): ComponentCallback = ComponentCallback(
    onReady = { paymentMethodComponent -> println("$paymentMethodComponent Ready") },
    onChange = { paymentMethodComponent ->
      viewModelScope.launch {
        println("${paymentMethodComponent.name} Changed, isValid: ${paymentMethodComponent.isValid()}, isAvailable: ${paymentMethodComponent.isAvailable()}")
      }
    },
    onSubmit = { paymentMethodComponent ->
      println("$paymentMethodComponent Submitted")
    },
    onError = { paymentMethodComponent, error ->
      println("$paymentMethodComponent Error: $error")
    },
    onSuccess = { paymentMethodComponent, paymentSessionResponse ->
      println("$paymentMethodComponent Success: $paymentSessionResponse")
    },

    onTokenized = ::onTokenize,

    handleSubmit = if (advancedSettings.value.submitPayment == SubmitPaymentHandler.HandleSubmit) ::handleSubmit else null,

    handleTap = if (advancedSettings.value.showHandleTapConfiguration) ::handleTap else null,
  )

  private suspend fun onTokenize(onTokenizeResult: TokenizationResult): CallbackResult {
    println("onTokenize: $onTokenizeResult")
    _screenState.update {
      (it as PaymentComponentScreenState).copy(tokenizedResult = onTokenizeResult)
    }
    return CallbackResult.Accepted
  }

  private suspend fun handleSubmit(sessionData: String): ApiCallResult = flowComponent.handleSubmit(sessionData, settingState.value)

  private suspend fun handleTap(paymentMethodComponent: PaymentMethodComponent): Boolean {
    println("handleTap: ${paymentMethodComponent.name}")
    return (screenState.value as? PaymentComponentScreenState)?.termAndConditions ?: false
  }

  override fun onCleared() {
    super.onCleared()
    flowComponent.onCleared()
  }
}
