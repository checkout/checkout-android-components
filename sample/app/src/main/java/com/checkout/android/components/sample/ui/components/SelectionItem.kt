package com.checkout.android.components.sample.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * A composable that represents a single item in a selection list.
 *
 * Displays a text label and an optional checkmark icon if the item is currently selected.
 *
 * @param text The text to be displayed for the item.
 * @param isSelected Whether the item should be shown as selected.
 */
@Composable
fun SelectionItem(
  text: String,
  isSelected: Boolean,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(modifier = Modifier.weight(1f), text = text)
    if (isSelected) {
      Icon(
        Icons.Default.Check,
        contentDescription = "Selected",
        modifier = Modifier.size(ICON_SIZE),
      )
    }
  }
}
