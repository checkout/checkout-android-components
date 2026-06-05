package com.checkout.android.components.sample.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.byValue
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.flow.drop

/**
 * A custom [OutlinedTextField] wrapper to provide a consistent text input experience
 * with pre-configured keyboard options and single-line constraints.
 */
@Composable
fun SampleOutlineTextField(
  label: String,
  state: TextFieldState,
  onDone: (String) -> Unit,
  modifier: Modifier = Modifier,
  keyboardType: KeyboardType = KeyboardType.Text,
  inputTransformation: InputTransformation? = null,
) {
  val keyboardController = LocalSoftwareKeyboardController.current
  val currentOnDone by rememberUpdatedState(onDone)

  LaunchedEffect(state) {
    snapshotFlow { state.text.toString() }
      .drop(1)
      .collect { currentOnDone(it) }
  }

  OutlinedTextField(
    modifier = modifier,
    state = state,
    label = {
      Text(text = label, style = MaterialTheme.typography.bodySmall)
    },
    keyboardOptions = KeyboardOptions(
      capitalization = KeyboardCapitalization.None,
      autoCorrectEnabled = false,
      keyboardType = keyboardType,
      imeAction = ImeAction.Done,
    ),
    lineLimits = TextFieldLineLimits.SingleLine,
    onKeyboardAction = KeyboardActionHandler {
      currentOnDone(state.text.toString())
      keyboardController?.hide()
    },
    inputTransformation = inputTransformation,
  )
}

@Composable
fun SampleNumberOutlinedTextField(
  label: String,
  modifier: Modifier = Modifier,
  currentValue: String = "",
  onDone: (String) -> Unit = {},
) {
  val state = rememberTextFieldState(currentValue)

  SampleOutlineTextField(
    label = label,
    modifier = modifier,
    keyboardType = KeyboardType.Phone,
    state = state,
    onDone = onDone,
    inputTransformation = InputTransformation.byValue { current, proposed ->
      if (proposed.all { it.isDigit() }) {
        proposed
      } else {
        current
      }
    },
  )
}

@Composable
fun SampleEmailOutlinedTextField(
  label: String,
  modifier: Modifier = Modifier,
  currentValue: String = "",
  onDone: (String) -> Unit = {},
) {
  val state = rememberTextFieldState(currentValue)

  SampleOutlineTextField(
    label = label,
    modifier = modifier,
    keyboardType = KeyboardType.Email,
    state = state,
    onDone = onDone,
  )
}

@Composable
fun SampleTextOutlinedTextField(
  label: String,
  modifier: Modifier = Modifier,
  currentValue: String = "",
  onDone: (String) -> Unit = {},
) {
  val state = rememberTextFieldState(currentValue)

  SampleOutlineTextField(
    label = label,
    modifier = modifier,
    keyboardType = KeyboardType.Text,
    state = state,
    onDone = onDone,
  )
}
