package com.checkout.android.components.sample.factory

import android.content.Context
import com.checkout.android.components.sample.core.BuildConfig
import com.checkout.android.components.sample.core.network.model.session.PaymentSessions
import com.checkout.android.components.sample.core.network.repository.PaymentSessionRepository
import com.checkout.android.components.sample.core.session.request.SubmitPaymentSession
import com.checkout.android.components.sample.extension.toDesignTokens
import com.checkout.android.components.sample.extension.toPaymentSessionLocale
import com.checkout.android.components.sample.ui.model.Components
import com.checkout.android.components.sample.ui.model.Environment
import com.checkout.android.components.sample.ui.model.PaymentMethods
import com.checkout.android.components.sample.ui.model.Settings
import com.checkout.android.components.sample.utils.toDesignTokens
import com.checkout.android.components.sample.utils.toPaymentSessionLocale
import com.checkout.components.core.CheckoutComponentsFactory
import com.checkout.components.interfaces.Environment
import com.checkout.components.interfaces.api.CheckoutComponents
import com.checkout.components.interfaces.api.PaymentMethodComponent
import com.checkout.components.interfaces.component.CheckoutComponentConfiguration
import com.checkout.components.interfaces.component.ComponentCallback
import com.checkout.components.interfaces.component.ComponentOption
import com.checkout.components.interfaces.model.ApiCallResult
import com.checkout.components.interfaces.model.ComponentName
import com.checkout.components.interfaces.model.PaymentMethodName
import com.checkout.components.interfaces.model.PaymentSessionResponse
import com.checkout.components.interfaces.model.paymentsession.PaymentSessionSubmissionResult
import com.checkout.components.wallet.wrapper.GooglePayFlowCoordinator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * A factory and helper class responsible for managing the initialization of checkout components.
 *
 * This class facilitates the creation of [CheckoutComponentConfiguration] by interacting with the [PaymentSessionRepository],
 * manages the instantiation of [CheckoutComponents], and provides specific payment method components like Flow and Card.
 * It also handles the integration of flow coordinators, such as Google Pay, and ensures activity results are
 * correctly propagated to the active checkout session.
 *
 * @property repository The repository used to create and manage payment sessions.
 */
class FlowComponent @Inject constructor(
  private val repository: PaymentSessionRepository,
) {

  private var checkoutComponent: CheckoutComponents? = null
  private var paymentSessionId: String? = null

  /**
   * Builds a [CheckoutComponentConfiguration] used to initialize checkout components for all payments relevant components.
   *
   * @param context The application context.
   * @param settings The user-defined settings for the checkout components.
   **/
  suspend fun createConfigurationFromSettings(
    context: Context,
    settings: Settings = Settings(),
    callbacks: ComponentCallback,
  ): CheckoutComponentConfiguration {
    val publicKey = when (settings.environment) {
      Environment.SANDBOX -> BuildConfig.SANDBOX_PUBLIC_KEY
      Environment.PRODUCTION -> BuildConfig.PRODUCTION_PUBLIC_KEY
    }

    val processingChannelId = when (settings.environment) {
      Environment.SANDBOX -> BuildConfig.SANDBOX_PROCESSING_CHANNEL_ID
      Environment.PRODUCTION -> BuildConfig.PRODUCTION_PROCESSING_CHANNEL_ID.ifEmpty { null }
    }

    val paymentSession = PaymentSessions(
      enabledPaymentMethods = enablePaymentMethod(settings),
      processingChannelID = processingChannelId,
      locale = settings.psLocale.toPaymentSessionLocale(),
    )

    val response = repository.createPaymentSession(
      environment = settings.environment,
      paymentSessions = paymentSession,
    ).also { paymentSessionId = it.id }

    val flowCoordinator = createGooglePlayFlowCoordinator(context, settings)?.let {
      mapOf(PaymentMethodName.GooglePay to it)
    } ?: mapOf()

    return CheckoutComponentConfiguration(
      context = context,
      publicKey = publicKey,
      environment = settings.environment,
      paymentSession = PaymentSessionResponse(
        response.id,
        response.secret,
      ),
      flowCoordinators = flowCoordinator,
      locale = settings.locale,
      appearance = settings.appearance.toDesignTokens(),
      componentCallback = callbacks,
    )
  }

  /**
   * Asynchronously creates an instance of [CheckoutComponents] using the provided configuration.
   *
   * This function runs on the IO dispatcher to offload the heavy initialization work from the main thread.
   * It creates a new [CheckoutComponents] instance via the [CheckoutComponentsFactory] and maintains
   * a reference to it for later activity result handling.
   *
   * @param configuration The configuration settings used to initialize the checkout components.
   *                      This should contain all necessary parameters like public key, environment,
   *                      payment session, and payment method coordinators.
   * @return An initialized [CheckoutComponents] instance ready to create specific payment method components.
   *
   * @throws Exception If the factory fails to create the components instance (propagated from factory)
   *
   * @see CheckoutComponentConfiguration
   * @see CheckoutComponentsFactory
   */
  suspend fun createComponents(
    configuration: CheckoutComponentConfiguration,
  ): CheckoutComponents = withContext(context = Dispatchers.IO) {
    CheckoutComponentsFactory(configuration).create().also { checkoutComponent = it }
  }

  /**
   * Creates a [PaymentMethodComponent] specifically for the Flow component.
   *
   * @param checkoutComponents The [CheckoutComponents] instance used to initialize the component.
   *                          This should be a previously created instance from [createComponents].
   * @param specificOptions Optional component-specific configuration options. If not provided,
   *                       default options will be used. Can be used to customize Flow behavior,
   *                       appearance, or other component-specific settings.
   * @return A [PaymentMethodComponent] configured for [ComponentName.Flow] that can be used
   *         to display payment method options to the user.
   *
   * @see CheckoutComponents.create
   * @see ComponentName.Flow
   * @see ComponentOption
   */
  fun createFlowPaymentMethodComponent(
    checkoutComponents: CheckoutComponents,
    specificOptions: ComponentOption? = null,
  ): PaymentMethodComponent = checkoutComponents.create(
    componentName = ComponentName.Flow,
    specificOptions = specificOptions,
  )

  /**
   * Creates a [PaymentMethodComponent] specifically for the Card payment method component.
   *
   * @param checkoutComponents The [CheckoutComponents] instance used to initialize the component.
   *                          This should be a previously created instance from [createComponents].
   * @param specificOptions Optional component-specific configuration options. If not provided,
   *                       default options will be used. Can be used to customize Card component behavior,
   *                       such as card validation rules, appearance, or input requirements.
   * @return A [PaymentMethodComponent] configured for [PaymentMethodName.Card]
   *
   * @see CheckoutComponents.create
   * @see PaymentMethodName.Card
   * @see ComponentOption
   */
  fun createCardPaymentMethodComponent(
    checkoutComponents: CheckoutComponents,
    specificOptions: ComponentOption? = null,
  ): PaymentMethodComponent = checkoutComponents.create(
    componentName = PaymentMethodName.Card,
    specificOptions = specificOptions,
  )

  /**
   * Cleans up the checkout component instance when the component is no longer needed.
   *
   * This function should be called when the hosting activity or view model is destroyed to ensure
   * proper resource cleanup and prevent memory leaks. It clears the internal reference to the
   * [CheckoutComponents] instance.
   */
  fun onCleared() {
    checkoutComponent = null
    paymentSessionId = null
  }

    suspend fun handleSubmit(
        sessionData: String,
        settings: Settings,
        amount: Int = 99999,
    ): ApiCallResult {
        val paymentSessionId = this.paymentSessionId ?: return ApiCallResult.Failure

        val environment = when (settings.environment) {
            LocalEnvironment.Sandbox -> Environment.SANDBOX
            LocalEnvironment.Production -> Environment.PRODUCTION
        }

        val response = repository.submitPaymentSession(
            environment = environment,
            submitPaymentSession = SubmitPaymentSession(
                sessionData = sessionData,
                amount = amount,
            ),
            paymentSessionId = paymentSessionId,
        )

        if (response.error != null) return ApiCallResult.Failure

        return ApiCallResult.Success(
            PaymentSessionSubmissionResult(
                id = response.id,
                type = response.type,
                status = response.status,
                action = response.action,
                declineReason = response.declineReason,
            ),
        )
    }

  private fun createGooglePlayFlowCoordinator(
    context: Context,
    settings: Settings,
  ): GooglePayFlowCoordinator? {
    val isGooglePayComponent = settings.component == Components.GooglePay
    val isFlowWithGooglePay = settings.component == Components.Flow &&
      PaymentMethods.GooglePay in settings.paymentMethods

    return if (isGooglePayComponent || isFlowWithGooglePay) {
      GooglePayFlowCoordinator(
        context = context,
        handleActivityResult = ::handleActivityResult,
      )
    } else {
      null
    }
  }

  private fun enablePaymentMethod(settings: Settings): List<String> = if (settings.component == Components.Flow) {
    settings.paymentMethods.map { it.name.lowercase() }
  } else {
    listOf(settings.component.name.lowercase())
  }

  private fun handleActivityResult(resultCode: Int, data: String) {
    checkoutComponent?.handleActivityResult(resultCode, data)
  }
}
