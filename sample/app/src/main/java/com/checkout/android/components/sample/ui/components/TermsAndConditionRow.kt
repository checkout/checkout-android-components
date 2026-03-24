package com.checkout.android.components.sample.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.checkout.android.components.sample.R
import com.checkout.android.components.sample.ui.theme.CheckoutComponentSampleTheme

/**
 * A composable row that displays a checkbox and the terms and conditions text.
 *
 * @param modifier The [Modifier] to be applied to this row.
 * @param checked Whether the terms and conditions checkbox is currently checked.
 * @param onCheckedChange The callback that is triggered when the checked state changes.
 */
@Composable
fun TermsAndConditionsRow(
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
      color = if (!checked) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
    )
  }
}

@Preview
@Composable
private fun TermsAndConditionsRowPreview() {
  CheckoutComponentSampleTheme {
    TermsAndConditionsRow()
  }
}
