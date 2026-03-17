package com.checkout.android.components.sample.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import com.checkout.android.components.sample.ui.model.PaymentResultState
import com.checkout.android.components.sample.ui.theme.CheckoutComponentSampleTheme
import com.checkout.components.interfaces.error.CheckoutError
import com.checkout.components.interfaces.error.CheckoutErrorCode
import com.checkout.components.interfaces.error.CheckoutErrorDetails
import com.checkout.components.interfaces.model.ComponentName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetResult(
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
        Text("Close")
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
      text = stringResource(R.string.payment_id, paymentResultState.paymentId),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyLarge,
    )

    Text(
      modifier = Modifier.fillMaxWidth(),
      text = "Generated Token: ${paymentResultState.token}",
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
      text = stringResource(R.string.label_payment_error),
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
      text = stringResource(R.string.payment_id, error.details.paymentSessionId),
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyLarge,
    )

    Text(
      modifier = Modifier.fillMaxWidth(),
      text = "Complete error: $error",
      textAlign = TextAlign.Center,
      style = MaterialTheme.typography.bodyLarge,
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun BottomSheetSuccessResultPreview() {
  CheckoutComponentSampleTheme {
    BottomSheetResult(
      modifier = Modifier,
      paymentResult = PaymentResultState("123456789", "123456789"),
    )
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun BottomSheetErrorResultPreview() {
  CheckoutComponentSampleTheme {
    BottomSheetResult(
      modifier = Modifier,
      paymentResult = PaymentResultState(
        error = CheckoutError.PaymentMethod(
          "Test Error",
          CheckoutErrorCode.PAYMENT_REQUEST_DECLINED,
          CheckoutErrorDetails.PaymentMethod(
            "session",
            "payment_sessiont_id",
            ComponentName.Flow,
          ),
        ),
      ),
    )
  }
}

@Preview
@Composable
private fun AmountPanelPreview() {
  CheckoutComponentSampleTheme {
    AmountPanel()
  }
}

@Preview
@Composable
private fun ValidatePaymentPreview() {
  CheckoutComponentSampleTheme {
    ValidatePayment()
  }
}
