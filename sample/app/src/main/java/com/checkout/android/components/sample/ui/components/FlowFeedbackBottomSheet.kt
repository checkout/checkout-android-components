package com.checkout.android.components.sample.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.checkout.android.components.sample.R
import com.checkout.android.components.sample.ui.model.PaymentResultState
import com.checkout.android.components.sample.ui.theme.CheckoutComponentSampleTheme
import com.checkout.components.interfaces.error.CheckoutError
import com.checkout.components.interfaces.error.CheckoutErrorCode
import com.checkout.components.interfaces.error.CheckoutErrorDetails
import com.checkout.components.interfaces.model.ComponentName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * A bottom sheet that displays the result for a payment flow.
 *
 * @param paymentResult The state containing the result of the payment process, including success details or error information.
 * @param modifier The [Modifier] to be applied to the bottom sheet layout.
 * @param onDismiss Callback invoked when the bottom sheet is dismissed or the close button is clicked.
 */
@Composable
fun FlowFeedbackBottomSheet(
  paymentResult: PaymentResultState,
  modifier: Modifier = Modifier,
  onDismiss: () -> Unit = {},
  sheetState: SheetState = rememberModalBottomSheetState(),
  scope: CoroutineScope = rememberCoroutineScope(),
) {
  ModalBottomSheet(
    modifier = modifier,
    onDismissRequest = {
      onDismiss()
    },

    sheetState = sheetState,
  ) {
    Column(
      modifier = Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(ROW_VERTICAL_PADDING),
    ) {
      if (paymentResult.isSuccess()) {
        SuccessBottomSheetBody(paymentResultState = paymentResult)
      } else if (paymentResult.isError()) {
        val error = paymentResult.error ?: return@Column
        ErrorBottomSheetBody(error)
      }

      Button(
        modifier = Modifier.padding(ELEMENT_SPACING),
        onClick = {
          scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
              onDismiss()
            }
          }
        },
      ) {
        Text(stringResource(R.string.label_button_close))
      }
    }
  }
}

@Composable
fun SuccessBottomSheetBody(
  paymentResultState: PaymentResultState,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(ROW_VERTICAL_PADDING),
  ) {
    Icon(
      modifier = Modifier.size(64.dp),
      imageVector = Icons.Default.Check,
      contentDescription = "success",
      tint = MaterialTheme.colorScheme.primary,
    )

    Text(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(R.string.label_payment_successful),
      textAlign = TextAlign.Center,
      color = MaterialTheme.colorScheme.primary,
      style = MaterialTheme.typography.displayLarge,
    )

    Text(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(R.string.label_error_payment_id, paymentResultState.paymentId),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyLarge,
    )

    Text(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(R.string.label_generated_token, paymentResultState.token),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyLarge,
    )
  }
}

@Composable
fun ErrorBottomSheetBody(
  error: CheckoutError,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(ROW_VERTICAL_PADDING),
  ) {
    Icon(
      modifier = Modifier.size(64.dp),
      imageVector = Icons.Default.Close,
      contentDescription = "failure",
      tint = MaterialTheme.colorScheme.tertiary,
    )

    Text(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(R.string.label_error_payment),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.displayLarge,
    )

    Text(
      modifier = Modifier.fillMaxWidth(),
      text = error.message,
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyLarge,
    )

    Text(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(R.string.label_error_payment_id, error.details.paymentSessionId),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyLarge,
    )

    Text(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource(R.string.label_error_complete, error),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyLarge,
    )
  }
}

@Preview
@Composable
private fun FlowFeedbackBottomSheetSuccessPreview() {
  CheckoutComponentSampleTheme {
    FlowFeedbackBottomSheet(
      modifier = Modifier,
      paymentResult = PaymentResultState("payment_id", "token"),
    )
  }
}

@Preview
@Composable
private fun FlowFeedbackBottomSheetErrorPreview() {
  CheckoutComponentSampleTheme {
    FlowFeedbackBottomSheet(
      modifier = Modifier,
      paymentResult = PaymentResultState(
        error = CheckoutError.PaymentMethod(
          "Test Error",
          CheckoutErrorCode.PAYMENT_REQUEST_DECLINED,
          CheckoutErrorDetails.PaymentMethod(
            "session",
            "payment_session_id",
            ComponentName.Flow,
          ),
        ),
      ),
    )
  }
}
