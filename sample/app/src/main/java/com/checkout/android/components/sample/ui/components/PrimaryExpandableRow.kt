package com.checkout.android.components.sample.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
