package com.checkout.android.components.sample.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.checkout.android.components.sample.R
import com.checkout.android.components.sample.ui.theme.CheckoutComponentSampleTheme

/**
 * A composable row that provides an input field and a button to update an amount.
 *
 * @param modifier The [Modifier] to be applied to the row layout.
 * @param state The [TextFieldState] used to manage the text input state.
 * @param onAmountChanged Callback invoked with the new integer amount when the update is triggered.
 */
@Composable
fun UpdateDetailsRow(
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

    SampleOutlineTextField(
      modifier = Modifier.weight(1f),
      label = "$",
      state = state,
      onDone = {
        updateAmount()
        focusManager.clearFocus()
      },
      keyboardType = KeyboardType.Number,
    )

    OutlinedButton(
      modifier = Modifier,
      onClick = {
        updateAmount()
        focusManager.clearFocus()
      },
    ) {
      Text(stringResource(R.string.label_update_amount))
    }
  }
}

@Preview
@Composable
private fun UpdateDetailsRowPreview() {
  CheckoutComponentSampleTheme {
    UpdateDetailsRow()
  }
}
