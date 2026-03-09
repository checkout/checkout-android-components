package com.checkout.android.components.sample.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

/**
 * A composable that displays a list of options where multiple items can be selected.
 *
 * @param modifier The [androidx.compose.ui.Modifier] to be applied to the list container.
 * @param options The full list of available options of type [T].
 * @param selectedOptions The list of options that are currently selected.
 * @param displayText A lambda function to determine the text to be displayed for each option.
 * @param onOptionSelected Callback invoked when an option is clicked.
 */
@Composable
fun <T> MultiSelectionList(
  modifier: Modifier = Modifier,
  options: List<T>,
  selectedOptions: List<T>,
  displayText: (T) -> String,
  onOptionSelected: (T) -> Unit,
) {
  val selectedSet = remember(selectedOptions) { selectedOptions.toSet() }
  Column(modifier = modifier) {
    options.forEachIndexed { index, option ->
      key(index) {
        val isSelected = option in selectedSet
        SelectionItem(
          modifier = Modifier.clickable(
            onClick = { onOptionSelected(option) },
            indication = null,
            interactionSource = null,
            enabled = true,
            onClickLabel = null,
            role = null,
          ),
          text = displayText(option),
          isSelected = isSelected,
        )
      }
    }
  }
}
