package com.checkout.android.components.sample.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * A composable that displays a dropdown menu for selecting a single option from a list.
 *
 * @param T The type of the options in the menu.
 * @param options The list of available options to display in the menu.
 * @param selectedOption The currently selected option.
 * @param displayText A function to determine the text representation of an option in the dropdown list.
 * @param selectedDisplayText A function to determine the text representation of the selected option shown in the collapsed state.
 * @param onOptionSelected A callback invoked when an option is selected from the menu.
 */
@Composable
fun <T> SingleSelectMenu(
  options: List<T>,
  selectedOption: T,
  displayText: (T) -> String = { it.toString() },
  selectedDisplayText: (T) -> String = { it.toString() },
  onOptionSelected: (T) -> Unit,
) {
  var expanded by remember { mutableStateOf(false) }
  Box {
    DropdownText(text = selectedDisplayText(selectedOption), onClick = { expanded = true })
    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false },
      containerColor = MaterialTheme.colorScheme.background,
    ) {
      options.forEachIndexed { index, option ->
        key(index) {
          val isSelected = option == selectedOption
          DropdownMenuItem(
            text = { Text(text = displayText(option)) },
            leadingIcon = {
              if (isSelected) {
                Icon(
                  Icons.Default.Check,
                  contentDescription = "Selected",
                  modifier = Modifier.size(ICON_SIZE),
                )
              }
            },
            onClick = {
              onOptionSelected(option)
              expanded = false
            },
          )
        }
      }
    }
  }
}
