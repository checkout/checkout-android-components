package com.checkout.android.components.sample.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * A small composable row that displays a primary text and a compact dropdown indicator.
 *
 * This composable is intended to be used as the visible trigger for dropdowns. It shows the
 * provided [text] styled with the primary color and displays a small "↕" indicator to the right.
 * Tapping the row invokes [onClick], which typically opens or toggles a dropdown menu.
 *
 * @param text The label or selected value to display.
 * @param onClick Callback invoked when the row is clicked.
 */
@Composable
fun DropdownText(
  text: String,
  onClick: () -> Unit,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.clickable(onClick = onClick),
  ) {
    Text(text = text, color = MaterialTheme.colorScheme.primary, fontSize = LABEL_FONT_SIZE)
    Icon(
      imageVector = Icons.Default.ArrowDropDown,
      tint = MaterialTheme.colorScheme.primary,
      contentDescription = null,
    )
  }
}
