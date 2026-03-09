package com.checkout.android.components.sample.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.checkout.android.components.sample.ui.theme.CheckoutGreen

/**
 * A composable containing a text label and a [androidx.compose.material3.Switch].
 *
 * @param label The text label displayed on the left side of the row.
 * @param isChecked The current checked state of the switch.
 * @param onCheckedChange Callback invoked when the switch state changes; receives the new state.
 */
@Composable
fun SwitchRow(label: String, isChecked: Boolean, onCheckedChange: (Boolean) -> Unit) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = SWITCH_ROW_PADDING),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Text(text = label, fontSize = LABEL_FONT_SIZE)
    Switch(
      checked = isChecked,
      onCheckedChange = onCheckedChange,
      colors = SwitchDefaults.colors(
        checkedThumbColor = MaterialTheme.colorScheme.background,
        checkedTrackColor = CheckoutGreen,
        uncheckedThumbColor = MaterialTheme.colorScheme.background,
        uncheckedTrackColor = MaterialTheme.colorScheme.surface,
      ),
    )
  }
}
