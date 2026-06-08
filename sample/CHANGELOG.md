# Changelog

## [2.0.0] - 2026-06-08

### Added
- **Tabby and Tamara support** — both APMs are available as standalone components and as options within the Flow component
- **Region Presets** — new preset selector in settings that populates currency, country, payment methods, locale, and customer email in one tap; available presets: UK, EU, MENA (AED), MENA (SAR), APAC
- **Currency selector** — manually override the payment session currency
- **Address Country selector** — manually override the billing/shipping country
- **Customer Email field** — configurable customer email included in payment session creation; MENA presets pre-fill `otp.success@tabby.ai` for Tabby test flows
- **Billing, shipping, and customer** fields now included in payment session creation
- **`reference`** field added to payment session requests

### Fixed
- Splash screen icon no longer cropped on Android 12+ (API 31+)

### Changed
- Default payment session amount updated to `10500` for AED support
- Phone country code is now derived from the selected country using the `Country` enum rather than being hardcoded
- Bumped `checkout-android-components` to `2.0.0`
- Added `redirect-handler`, `payment-methods-core`, and `payment-methods-redirect` artifacts

## [1.11.0]

- Bumped `checkout-android-components` to `1.11.0`

## [1.9.0]

- Bumped `checkout-android-components` to `1.9.0`
- Added Google Pay (wallet component)
