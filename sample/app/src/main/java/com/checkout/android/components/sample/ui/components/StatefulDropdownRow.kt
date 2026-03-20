package com.checkout.android.components.sample.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * A composable that displays a label and a single-selection dropdown menu.
 *
 * This component places a label on the left and a [SingleSelectMenu] on the right.
 * It is generic over the option type `T` so it can be reused with any data model.
 *
 * @param T The type of the options.
 * @param label The text label displayed at the start of the row.
 * @param options The list of available options to present in the dropdown.
 * @param selectedValue The currently selected option.
 * @param onValueChange Callback invoked with the newly selected value when the user picks an option.
 */
@Composable
fun <T> StatefulDropdownRow(
  label: String,
  options: List<T>,
  selectedValue: T,
  onValueChange: (T) -> Unit,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = ROW_VERTICAL_PADDING),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(ELEMENT_SPACING),
  ) {
    Text(text = label, fontSize = LABEL_FONT_SIZE)
    SingleSelectMenu(
      options = options,
      selectedOption = selectedValue,
      onOptionSelected = onValueChange,
    )
  }
}
