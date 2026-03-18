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
import com.checkout.android.components.sample.ui.model.PaymentResultState
import com.checkout.android.components.sample.ui.model.RememberMeSettings
import com.checkout.android.components.sample.ui.model.SettingScreenState
import com.checkout.android.components.sample.ui.model.Settings
import com.checkout.android.components.sample.ui.model.SubmitPaymentHandler
import com.checkout.components.interfaces.api.PaymentMethodComponent
import com.checkout.components.interfaces.component.CardConfiguration
import com.checkout.components.interfaces.component.ComponentCallback
import com.checkout.components.interfaces.component.ComponentOption
import com.checkout.components.interfaces.component.GooglePayConfiguration
import com.checkout.components.interfaces.component.PaymentButtonAction
import com.checkout.components.interfaces.component.RememberMeConfiguration
import com.checkout.components.interfaces.model.ApiCallResult
import com.checkout.components.interfaces.model.CallbackResult
import com.checkout.components.interfaces.model.CardMetadata
import com.checkout.components.interfaces.model.Phone
import com.checkout.components.interfaces.model.TokenizationResult
import com.checkout.components.interfaces.model.UpdateDetails
import com.checkout.components.wallet.WalletComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
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
  private val _rememberMeSettings = MutableStateFlow(RememberMeSettings())

  val screenState = _screenState.asStateFlow()

  val settingState = _settingState.asStateFlow()

  val advancedSettings = _advancedSettings.asStateFlow()

  val rememberMeSettings = _rememberMeSettings.asStateFlow()

  fun showFlowComponent(context: Context) {
    viewModelScope.launch {
      val advancedSettings = advancedSettings.value
      val config = flowComponent.createConfigurationFromSettings(
        context = context,
        settings = settingState.value,
        callbacks = buildComponentCallbacks(),
      )

      val component = try {
        flowComponent.createComponents(config)
      } catch (e: Exception) {
        if (e is CancellationException) throw e

        return@launch
      }

      val rememberMeConfiguration = if (rememberMeSettings.value.enableRememberMe) {
        val rememberMeSettings = _rememberMeSettings.value
        val phone =
          if (rememberMeSettings.phoneNumber.isNotEmpty() && rememberMeSettings.countryCode.isNotEmpty()) {
            Phone(
              number = rememberMeSettings.phoneNumber,
              countryCode = rememberMeSettings.countryCode,
            )
          } else {
            null
          }

        RememberMeConfiguration(
          showPayButton = rememberMeSettings.showRememberMePayButton,
          data = RememberMeConfiguration.Data(
            email = rememberMeSettings.email.takeIf { it.isNotEmpty() },
            phone = phone,
          ),
          acceptedCardSchemes = advancedSettings.rememberMeAcceptedScheme,
          acceptedCardTypes = advancedSettings.rememberMeAcceptedTypes,
        )
      } else {
        null
      }

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
        rememberMeConfiguration = rememberMeConfiguration,
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

  fun updateRememberMeSettings(rememberMeSettings: RememberMeSettings) {
    _rememberMeSettings.update {
      rememberMeSettings
    }
  }

  fun onSubmit() {
    viewModelScope.launch {
      (screenState.value as? PaymentComponentScreenState)?.run {
        if (advancedSettings.value.customButtonType == PaymentButtonAction.PAYMENT) {
          paymentComponent.submit()
        } else {
          paymentComponent.tokenize()
        }
      }
    }
  }

  fun onAmountChanged(amount: Int) {
    _screenState.update {
      it.runIfPaymentComponentScreenState {
        paymentComponent.update(UpdateDetails(amount = amount))
        this@runIfPaymentComponentScreenState
      }
    }
  }

  fun onCheckTermsAndConditions(value: Boolean) {
    _screenState.update {
      it.runIfPaymentComponentScreenState {
        copy(termsAndConditionsAccepted = value)
      }
    }
  }

  fun onDismissBottomSheet() {
    _screenState.update {
      it.runIfPaymentComponentScreenState {
        copy(paymentResult = PaymentResultState())
      }
    }
  }

  override fun onCleared() {
    super.onCleared()
    flowComponent.onCleared()
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
      _screenState.update {
        it.runIfPaymentComponentScreenState {
          copy(paymentResult = paymentResult.copy(error = error))
        }
      }
    },
    onSuccess = { paymentMethodComponent, paymentSessionResponse ->
      println("$paymentMethodComponent Success: $paymentSessionResponse")

      _screenState.update { state ->
        state.runIfPaymentComponentScreenState {
          val shouldShowNoToken =
            paymentResult.token.isEmpty() && paymentMethodComponent is WalletComponent
          val finalToken =
            if (shouldShowNoToken) "no tokenization" else paymentResult.token

          copy(
            paymentResult = paymentResult.copy(
              paymentId = paymentSessionResponse,
              token = finalToken,
            ),
          )
        }
      }
    },

    onCardBinChanged = ::onCardBinChanged,

    onTokenized = ::onTokenize,

    handleSubmit = if (advancedSettings.value.submitPayment == SubmitPaymentHandler.HandleSubmit) ::handleSubmit else null,

    handleTap = if (advancedSettings.value.showHandleTapConfiguration) ::handleTap else null,
  )

  private suspend fun onTokenize(tokenizeResult: TokenizationResult): CallbackResult {
    println("onTokenize: $tokenizeResult")
    _screenState.update { state ->
      state.runIfPaymentComponentScreenState {
        val advancedSettings = advancedSettings.value

        if (advancedSettings.paymentAction == PaymentButtonAction.TOKENIZE ||
          (!advancedSettings.showCardPayButton && advancedSettings.customButtonType == PaymentButtonAction.TOKENIZE)
        ) {
          copy(
            paymentResult = paymentResult.copy(
              token = tokenizeResult.data.token,
              paymentId = "Payment Action Tokenize",
            ),
          )
        } else {
          copy(paymentResult = paymentResult.copy(token = tokenizeResult.data.token))
        }
      }
    }
    return CallbackResult.Accepted
  }

  private fun onCardBinChanged(cardMetadata: CardMetadata): CallbackResult {
    println("onCardBinChanged: $cardMetadata")
    return CallbackResult.Accepted
  }

  private suspend fun handleSubmit(sessionData: String): ApiCallResult = flowComponent.handleSubmit(sessionData, settingState.value)

  @Suppress("RedundantSuspendModifier") // handleTap callback is a suspend function, even if we are not have any suspended call
  private suspend fun handleTap(paymentMethodComponent: PaymentMethodComponent): Boolean {
    println("handleTap: ${paymentMethodComponent.name}")
    return (screenState.value as? PaymentComponentScreenState)?.termsAndConditionsAccepted ?: false
  }

  fun MainScreenState.runIfPaymentComponentScreenState(
    block: PaymentComponentScreenState.() -> MainScreenState,
  ): MainScreenState = if (this is PaymentComponentScreenState) {
    block()
  } else {
    this
  }
}
