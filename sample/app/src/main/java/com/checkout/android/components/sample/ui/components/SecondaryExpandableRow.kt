package com.checkout.android.components.sample.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate

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
