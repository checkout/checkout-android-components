package com.checkout.android.components.sample.extension

import com.checkout.android.components.sample.ui.model.Appearance
import com.checkout.components.interfaces.localisation.Locale
import com.checkout.components.interfaces.localisation.Locale.Ar
import com.checkout.components.interfaces.localisation.Locale.Da
import com.checkout.components.interfaces.localisation.Locale.De
import com.checkout.components.interfaces.localisation.Locale.El
import com.checkout.components.interfaces.localisation.Locale.En
import com.checkout.components.interfaces.localisation.Locale.Es
import com.checkout.components.interfaces.localisation.Locale.Fi
import com.checkout.components.interfaces.localisation.Locale.Fil
import com.checkout.components.interfaces.localisation.Locale.Fr
import com.checkout.components.interfaces.localisation.Locale.Hi
import com.checkout.components.interfaces.localisation.Locale.Id
import com.checkout.components.interfaces.localisation.Locale.It
import com.checkout.components.interfaces.localisation.Locale.Ja
import com.checkout.components.interfaces.localisation.Locale.Ms
import com.checkout.components.interfaces.localisation.Locale.Nb
import com.checkout.components.interfaces.localisation.Locale.Nl
import com.checkout.components.interfaces.localisation.Locale.Pt
import com.checkout.components.interfaces.localisation.Locale.Sv
import com.checkout.components.interfaces.localisation.Locale.Th
import com.checkout.components.interfaces.localisation.Locale.Vi
import com.checkout.components.interfaces.localisation.Locale.Zh
import com.checkout.components.interfaces.localisation.Locale.ZhHk
import com.checkout.components.interfaces.localisation.Locale.ZhTw
import com.checkout.components.interfaces.uicustomisation.designtoken.ColorTokens
import com.checkout.components.interfaces.uicustomisation.designtoken.DefaultBorderRadius
import com.checkout.components.interfaces.uicustomisation.designtoken.DefaultFonts
import com.checkout.components.interfaces.uicustomisation.designtoken.DesignTokens
import com.checkout.components.interfaces.uicustomisation.font.FontName

/**
 * Maps the [Appearance] selection to its corresponding [DesignTokens] configuration.
 * @return A [DesignTokens] object containing color, font, and radius specifications,
 * or null if no custom tokens are required.
 */
fun Appearance.toDesignTokens(): DesignTokens? = when (this) {
  Appearance.Light -> null

  Appearance.Dark -> DesignTokens(
    colorTokens = ColorTokens(
      colorBackground = 0xFF0D1117,
      colorAction = 0xFF186AFF,
      colorBorder = 0xFF30363D,
      colorDisabled = 0xFF6E7681,
      colorError = 0xFFFF591A,
      colorFormBackground = 0xFF161B22,
      colorFormBorder = 0xFF30363D,
      colorInverse = 0xFFFFFFFF,
      colorOutline = 0xFF4098FF,
      colorPrimary = 0xFFC9D1D9,
      colorSecondary = 0xFF8B949E,
      colorSuccess = 0xFF85FF1A,
    ),
    fonts = mapOf(
      FontName.Footnote to DefaultFonts.FOOTNOTE,
      FontName.Input to DefaultFonts.INPUT,
      FontName.Label to DefaultFonts.LABEL,
      FontName.Button to DefaultFonts.BUTTON,
      FontName.Heading to DefaultFonts.HEADING,
      FontName.Subheading to DefaultFonts.SUBHEADING,
    ),
    borderFormRadius = DefaultBorderRadius.FORM,
    borderButtonRadius = DefaultBorderRadius.BUTTON,
  )
}

/**
 * Converts a [Locale] object to its corresponding string representation for a payment session.
 *
 * @return A [String] representing the locale in a format compatible with payment sessions (e.g., "en-GB"),
 * or `null` if the locale is null or [Locale.Customised].
 */
fun Locale?.toPaymentSessionLocale(): String? = when (this) {
  Ar -> "ar"
  Da -> "da-DK"
  De -> "de-DE"
  El -> "el"
  En -> "en-GB"
  Es -> "es-ES"
  Fi -> "fi-FI"
  Fil -> "fil-PH"
  Fr -> "fr-FR"
  Hi -> "hi-IN"
  Id -> "id-ID"
  It -> "it-IT"
  Ja -> "ja-JP"
  Ms -> "ms-MY"
  Nb -> "nb-NO"
  Nl -> "nl-NL"
  Pt -> "pt-PT"
  Sv -> "sv-SE"
  Th -> "th-TH"
  Vi -> "vi-VN"
  Zh -> "zh-CN"
  ZhHk -> "zh-HK"
  ZhTw -> "zh-TW"
  is Locale.Customised, null -> null
}
