package com.checkout.android.components.sample.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * A composable that displays a label and a dropdown menu in a horizontal row.
 * This is typically used for secondary configuration settings.
 *
 * @param T The type of the items in the dropdown options.
 * @param label The text to be displayed as the row label.
 * @param options The list of selectable items to be displayed in the dropdown menu.
 * @param selectedValue The currently selected item.
 * @param selectedDisplayText A function to convert the selected item into a string for the collapsed menu view.
 * @param displayText A function to convert an item into a string for the menu list items.
 * @param onValueChange A callback triggered when a new item is selected from the menu.
 */
@Composable
fun <T> SecondaryDropdownRow(
  label: String,
  options: List<T>,
  selectedValue: T,
  selectedDisplayText: (T) -> String = { it.toString() },
  displayText: (T) -> String = { it.toString() },
  onValueChange: (T) -> Unit,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = ROW_VERTICAL_PADDING),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Text(text = label, style = MaterialTheme.typography.bodyLarge)
    SingleSelectMenu(
      options = options,
      displayText = displayText,
      selectedDisplayText = selectedDisplayText,
      selectedOption = selectedValue,
      onOptionSelected = onValueChange,
    )
  }
}
