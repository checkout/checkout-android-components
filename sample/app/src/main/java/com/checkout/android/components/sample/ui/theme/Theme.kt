package com.checkout.android.components.sample.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
  primary = CheckoutBlue,
  onPrimary = CheckoutWhite,
  secondary = CheckoutPurple,
  onSecondary = CheckoutWhite,
  tertiary = CheckoutOrange,
  background = CheckoutBlack,
  surface = CheckoutEbony,
  onBackground = CheckoutOffWhite,
  onSurface = CheckoutWhite,
  surfaceVariant = CheckoutDarkGrey,
  onSurfaceVariant = CheckoutGrey,
)

private val LightColorScheme = lightColorScheme(
  primary = CheckoutBlue,
  onPrimary = CheckoutWhite,
  secondary = CheckoutPurple,
  onSecondary = CheckoutWhite,
  tertiary = CheckoutOrange,
  background = CheckoutWhite,
  surface = CheckoutOffWhite,
  onBackground = CheckoutEbony,
  onSurface = CheckoutEbony,
  surfaceVariant = Color(0xFFE0DDDD),
  onSurfaceVariant = CheckoutGrey,
)

@Composable
fun CheckoutComponentSampleTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      val context = LocalContext.current
      if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    }

    darkTheme -> DarkColorScheme

    else -> LightColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content,
  )
}
