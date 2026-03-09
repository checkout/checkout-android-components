package com.checkout.android.components.sample.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.checkout.android.components.sample.ui.theme.CheckoutGreen

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

/**
 * A composable that can be expanded or collapsed to show or hide content.
 *
 * This component features a label and a rotating arrow icon to indicate the current state.
 * It uses Material's TextButton styling and provides animated visibility for the content.
 *
 * @param label The text displayed in the header of the row.
 * @param initiallyExpanded Whether the content should be visible by default.
 * @param content The composable content to be displayed when the row is expanded.
 */
@Composable
fun PrimaryExpandableRow(
  label: String,
  initiallyExpanded: Boolean = false,
  content: @Composable () -> Unit,
) {
  var isExpanded by remember { mutableStateOf(initiallyExpanded) }
  val rotationState by animateFloatAsState(
    targetValue = if (isExpanded) 180f else 0f,
    label = "Arrow Rotation",
  )

  Column(modifier = Modifier.fillMaxWidth()) {
    TextButton(
      onClick = { isExpanded = !isExpanded },
      colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary),
    ) {
      Row(
        modifier = Modifier
          .padding(vertical = HEADER_VERTICAL_PADDING)
          .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
      ) {
        Text(text = label, fontSize = LABEL_FONT_SIZE)
        Icon(
          imageVector = Icons.Default.KeyboardArrowDown,
          contentDescription = "Expand",
          tint = MaterialTheme.colorScheme.primary,
          modifier = Modifier.rotate(rotationState),
        )
      }
    }
    AnimatedVisibility(visible = isExpanded) {
      content()
    }
  }
}

/**
 * A composable containing a text label and a [Switch].
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
    Text(text = label, fontSize = LABEL_FONT_SIZE)
    SingleSelectMenu(
      options = options,
      displayText = displayText,
      selectedDisplayText = selectedDisplayText,
      selectedOption = selectedValue,
      onOptionSelected = onValueChange,
    )
  }
}

/**
 * A composable that can be expanded or collapsed to show or hide content.
 *
 * This component features a label and a rotating arrow icon to indicate the current state.
 * Unlike [PrimaryExpandableRow], this version uses a simpler clickable Modifier without
 * the material button styling.
 *
 * @param label The text displayed in the header of the row.
 * @param initiallyExpanded Whether the content should be visible by default.
 * @param content The composable content to be displayed when the row is expanded.
 */
@Composable
fun SecondaryExpandableRow(
  label: String,
  initiallyExpanded: Boolean = false,
  content: @Composable () -> Unit,
) {
  var isExpanded by remember { mutableStateOf(initiallyExpanded) }
  val rotationState by animateFloatAsState(
    targetValue = if (isExpanded) 0f else -90f,
    label = "Arrow Rotation",
  )

  Column(
    modifier = Modifier
      .clickable(
        onClick = { isExpanded = !isExpanded },
        indication = null,
        interactionSource = null,
        enabled = true,
        onClickLabel = null,
        role = null,
      )
      .fillMaxWidth(),
  ) {
    Row(
      modifier = Modifier
        .padding(vertical = HEADER_VERTICAL_PADDING)
        .fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Text(text = label, fontSize = LABEL_FONT_SIZE)
      Icon(
        imageVector = Icons.Default.KeyboardArrowDown,
        contentDescription = "Expand",
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.rotate(rotationState),
      )
    }

    AnimatedVisibility(visible = isExpanded) {
      content()
    }
  }
}

/**
 * A composable that displays a list of options where multiple items can be selected.
 *
 * @param modifier The [Modifier] to be applied to the list container.
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

// ============================================================================
// Dimension Constants
// ============================================================================

/** Standard icon size used throughout the component library. */
private val ICON_SIZE = 18.dp

/** Standard padding for vertical spacing in rows. */
private val ROW_VERTICAL_PADDING = 12.dp

/** Standard padding for header rows. */
private val HEADER_VERTICAL_PADDING = 16.dp

/** Standard horizontal spacing between elements. */
private val ELEMENT_SPACING = 8.dp

/** Small vertical padding used in switch rows. */
private val SWITCH_ROW_PADDING = 4.dp

/** Minimal vertical padding used for text adjustments. */
private val MINIMAL_PADDING = 2.dp

// ============================================================================
// Font Size Constants
// ============================================================================

/** Standard text font size for labels and menu items. */
private val LABEL_FONT_SIZE = 16.sp
