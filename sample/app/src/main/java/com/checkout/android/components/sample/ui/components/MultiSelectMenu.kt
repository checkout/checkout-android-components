package com.checkout.android.components.sample.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

/**
 * A composable menu that allows for multiple items to be selected simultaneously.
 *
 * @param T The type of the options provided in the menu.
 * @param options The list of all available options to display in the dropdown.
 * @param selectedOptions A set containing the currently selected items.
 * @param onOptionToggled A callback triggered when an option is clicked, returning the toggled item.
 */
@Composable
fun <T> MultiSelectMenu(
  options: List<T>,
  selectedOptions: Set<T>,
  onOptionToggled: (T) -> Unit,
) {
  var expanded by remember { mutableStateOf(false) }

  val displayText = if (selectedOptions.isEmpty()) {
    "Select..."
  } else {
    selectedOptions.joinToString(",\n")
  }

  Box {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.clickable { expanded = true },
    ) {
      Text(
        text = displayText,
        color = MaterialTheme.colorScheme.primary,
        fontSize = LABEL_FONT_SIZE,
        textAlign = TextAlign.Center,
      )
    }

    DropdownMenu(
      expanded = expanded,
      onDismissRequest = { expanded = false },
      containerColor = MaterialTheme.colorScheme.background,
    ) {
      options.forEachIndexed { index, option ->
        key(index) {
          val isSelected = option in selectedOptions
          DropdownMenuItem(
            text = { Text(text = option.toString()) },
            leadingIcon = {
              if (isSelected) {
                Icon(
                  Icons.Default.Check,
                  contentDescription = "Selected",
                  modifier = Modifier.size(ICON_SIZE),
                )
              }
            },
            onClick = { onOptionToggled(option) },
          )
        }
      }
    }
  }
}
