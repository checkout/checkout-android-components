# Checkout Components Sample App

A comprehensive Android sample application demonstrating the capabilities of the Checkout Android Components library. This app showcases payment integration features including card payments, Google Pay, and Remember Me functionality.

## Table of Contents

- [Overview](#overview)
- [Minimum Requirements](#minimum-requirements)
- [Project Setup](#project-setup)
- [Installation & Configuration](#installation--configuration)
- [Features](#features)
- [Basic Settings](#basic-settings)
- [Advanced Settings](#advanced-settings)
- [Remember Me Settings](#remember-me-settings)
- [Architecture](#architecture)
- [Contributing](#contributing)

---

## Overview

This sample application is a **production-ready reference implementation** for integrating the Checkout Android Components SDK into Android applications. It provides:

- **Flow Component** - A complete payment flow with multiple payment methods
- **Card Component** - Standalone card payment processing
- **Google Pay Integration** - Native Google Pay support
- **Remember Me** - Save and reuse payment methods
- **Customizable UI** - Full theming and localization support
- **Sandbox & Production Environments** - Easy switching between test and live environments

---

## Minimum Requirements

### Development Environment

| Component          | Minimum Version                      |
|--------------------|--------------------------------------|
| **Java**           | 11 (JVM compatibility)               |
| **Kotlin**         | 2.2.0 or later                       |
| **Android SDK**    | API Level 24 (Android 7.0) or higher |
| **Gradle**         | 9.0+ (Gradle Wrapper included)       |

### Build Configuration

- **Target SDK**: 36 (Android 15)
- **Compile SDK**: 36
- **Min SDK**: 24 (Android 7.0)

### Runtime Requirements

- **Android Device/Emulator**: Android 7.0 (API 24) or higher

---

## Project Setup

### Prerequisites

Before cloning and running the project, ensure you have:

1. **Android Studio** installed (2024.1 or later)
2. **Java 11** installed and configured
3. **Git** installed
4. An active **Checkout account** with API credentials

### Cloning the Repository

```bash
git clone https://github.com/your-organization/checkout-android-components.git
cd checkout-android-components/sample
```

### Directory Structure

```
sample/
├── app/                          # Main application module
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/            # Kotlin source code
│   │   │   ├── res/             # Resources (strings, colors, etc.)
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                # Unit tests
│   │   └── androidTest/         # Instrumentation tests
│   └── build.gradle.kts         # App module build configuration
├── core/                         # Core library module
│   ├── src/
│   │   ├── main/
│   │   ├── test/
│   │   └── androidTest/
│   └── build.gradle.kts         # Core module build configuration
├── gradle/
│   ├── libs.versions.toml       # Dependency versions
│   └── wrapper/                 # Gradle wrapper
├── build.gradle.kts             # Root build configuration
├── gradle.properties            # Global Gradle properties
├── local.properties             # Local SDK configuration (auto-generated)
├── settings.gradle.kts          # Project structure definition
└── README.md                    # This file
```

---

## Installation & Configuration

### Step 1: Open Project in Android Studio

1. Launch Android Studio
2. Click **File** → **Open**
3. Navigate to the cloned `sample` directory and select it
4. Android Studio will sync the Gradle project automatically

### Step 2: Configure API Credentials

The project uses Checkout API credentials for Sandbox and Production environments.

#### Manual Configuration

1. Create/Update `local.properties` in the project root:

```properties
sdk.dir=/path/to/your/android/sdk

# Sandbox Environment
sandbox.components.public_key=YOUR_SANDBOX_PUBLIC_KEY
sandbox.components.secret_key=YOUR_SANDBOX_SECRET_KEY
sandbox.components.processing_channel_id=YOUR_SANDBOX_PROCESSING_CHANNEL_ID

# Production Environment
production.components.public_key=YOUR_PRODUCTION_PUBLIC_KEY
production.components.secret_key=YOUR_PRODUCTION_SECRET_KEY
production.components.processing_channel_id=YOUR_PRODUCTION_PROCESSING_CHANNEL_ID
```

### Step 3: Verify Android SDK

1. In Android Studio, go to **Tools** → **SDK Manager**
2. Ensure you have:
	- Android SDK Build-Tools 36.x
	- Android API 36 Platform
	- Android SDK Tools
3. Update if necessary

### Step 4: Sync Gradle

1. Click **File** → **Sync Now** or use **Ctrl+Shift+O** (macOS: **Cmd+Shift+O**)
2. Wait for Gradle to finish syncing
3. Build the project: **Build** → **Make Project** or **Ctrl+B** (macOS: **Cmd+B**)

### Step 5: Run the Application

1. Connect an Android device or launch an emulator
2. Click **Run** → **Run 'app'** or press **Shift+F10** (macOS: **Ctrl+R**)
3. Select your target device
4. The application will build and launch

---

## Features


| Feature                 | Description                                                                         |
|-------------------------|-------------------------------------------------------------------------------------|
| **Flow Component**      | Complete payment flow with multiple payment component methods                       |
| **Card Component**      | Standalone card payment processing                                                  |
| **Google Pay**          | Native Google Pay support                                                           |
| **Component callbacks** | Configuration examples for **HandleSubmit**, **HandleTap** and **onCardBinChanged** |
| **Remember Me**         | Save and reuse payment methods                                                      |
| **Custom UI**           | Full theming and localization support                                               |
| **Sandbox & Prod**      | Easy switching between test and live environments                                   |
| **Custom Pay Button**   | Use custom pay button for Payment or Tokenization                                   |
| **Address component**   | Standalone component for address                                                    |

---



### Supported Payment Methods

- **Card Payments** - Visa, Mastercard, American Express, etc.
- **Google Pay** - Fast, secure payment using Google Pay
- **Remember Me** - Store and reuse payment methods securely

### Core Capabilities

- **Multi-locale Support** - UI in multiple languages
- **Dark Mode Support** - Full dark theme compatibility

---

## Basic Settings

Access basic settings by clicking the **Settings icon** (⚙️) in the app header.

### Component Selection

| Setting             | Default         | Options                     | Description                                              |
|---------------------|-----------------|-----------------------------|----------------------------------------------------------|
| **Component Type**  | Flow            | `Flow`, `Card`, `GooglePay` | Select the primary payment component                     |
| **Payment Methods** | Card, GooglePay | Multiple selection          | When using Flow, choose which payment methods to display |

### Environment Configuration

| Setting         | Default | Options                 | Description                                     |
|-----------------|---------|-------------------------|-------------------------------------------------|
| **Environment** | Sandbox | `Sandbox`, `Production` | Select between test and live payment processing |
| **Appearance**  | Light   | `Light`, `Dark`         | Application theme preference                    |

### Localization

| Setting       | Default | Options       | Description                           |
|---------------|---------|---------------|---------------------------------------|
| **Locale**    | English | 20+ languages | UI language for the checkout flow     |
| **PS Locale** | English | 20+ languages | Language for payment session creation |

**Example**: Switch to Sandbox environment for testing before going live:

1. Tap Settings ⚙️
2. Select **Environment** → **Sandbox**
3. Tap outside to apply

---

## Advanced Settings

Advanced settings provide granular control over payment processing. Expand the **Advanced Features** section in the Settings screen.

### Payment Button Configuration

| Setting                     | Default | Description                                  |
|-----------------------------|---------|----------------------------------------------|
| **Show Card Pay Button**    | Enabled | Display a dedicated button for card payments |
| **Payment Button Action**   | PAYMENT | Choose action type: `PAYMENT`, `AUTHORIZE`   |
| **Display Cardholder Name** | TOP     | Position: `TOP`, `BOTTOM`, `HIDDEN`          |

### Card Scheme Management

Control which card types are accepted for different payment methods:

| Setting                          | Default | Description                                    |
|----------------------------------|---------|------------------------------------------------|
| **Card Accepted Schemes**        | All     | Visa, Mastercard, Amex, Diners, Discover, etc. |
| **GooglePay Accepted Schemes**   | All     | Card schemes supported by Google Pay           |
| **Remember Me Accepted Schemes** | All     | Card schemes for stored payment methods        |

**Example**: Accept only Visa and Mastercard:

1. Tap Settings ⚙️
2. Expand **Advanced Features**
3. Tap **Card Accepted Schemes**
4. Deselect unwanted schemes
5. Confirm changes

### Card Type Management

Fine-tune card acceptance by type:

| Setting                        | Description                    |
|--------------------------------|--------------------------------|
| **Card Accepted Types**        | Specific types for Card        |
| **GooglePay Accepted Types**   | Specific types for Google Pay  |
| **Remember Me Accepted Types** | Specific types for Remember Me |

### Payment Submission Handling

| Setting                    | Default | Options               | Description                            |
|----------------------------|---------|-----------------------|----------------------------------------|
| **Submit Payment Handler** | SDK     | `SDK`, `HandleSubmit` | Control who handles payment submission |

- **SDK**: Framework handles submission automatically
- **HandleSubmit**: App receives callbacks for custom handling

### Additional Options

| Setting                      | Default           | Description                                              |
|------------------------------|-------------------|----------------------------------------------------------|
| **Show Update Amount View**  | Disabled          | Allow users to modify transaction amount                 |
| **Custom Button Type**       | PAYMENT, TOKENIZE | Set action type for custom buttons                       |
| **Address Configuration**    | Empty             | `Empty`, `Billing`, `Shipping` - Address collection mode |
| **Handle Tap Configuration** | Disabled          | Enable sample of Terms & Conditions handling             |

**Advanced Example**: Set up custom payment submission with amount updates:

1. Tap Settings ⚙️
2. Expand **Advanced Features**
3. Set **Submit Payment Handler** → **HandleSubmit**
4. Enable **Show Update Amount View**
5. Set **Custom Button Type** → **PAYMENT** or **TOKENIZE**

---

## Remember Me Settings

The "Remember Me" feature allows users to securely save and reuse payment methods for faster future transactions.

Expand the **Remember Me Configuration** section in Settings to customize this feature.

### Remember Me Settings

| Setting                         | Default  | Type   | Description                                  |
|---------------------------------|----------|--------|----------------------------------------------|
| **Enable Remember Me**          | Disabled | Toggle | Master switch to enable/disable the feature  |
| **Show Remember Me Pay Button** | Disabled | Toggle | Display a dedicated button for saved methods |


When "Remember Me" is enabled, provide customer details:

| Field            | Format             | Required | Description                               |
|------------------|--------------------|----------|-------------------------------------------|
| **Email**        | `user@example.com` | Yes      | Customer email address for identification |
| **Country Code** | `+1`, `+44`, `+33` | Yes      | International dialing code                |
| **Phone Number** | `1234567890`       | Yes      | Customer contact number                   |

### Accepted Payment Methods

Configure which card schemes and types are accepted for saved methods:

| Setting                          | Default | Description          |
|----------------------------------|---------|----------------------|
| **Remember Me Accepted Schemes** | All     | Allowed card schemes |
| **Remember Me Accepted Types**   | All     | Allowed card types   |

### Remember Me

[Enable Remember Me](https://www.checkout.com/docs/payments/accept-payments/accept-a-payment-on-your-mobile-app/extend-your-flow-for-mobile-integration/enable-remember-me)

### Example: Enable Remember Me Feature

1. Tap Settings ⚙️
2. Expand **Remember Me Configuration**
3. Toggle **Enable Remember Me** ON
4. Enter customer **Email**: `customer@example.com`
5. Set **Country Code**: `+1`
6. Set **Phone Number**: `2025551234`
7. Toggle **Show Remember Me Pay Button** ON
8. Configure accepted schemes and types as needed
9. Start Flow

---

## Architecture

### Project Structure

```
app/src/main/java/com/checkout/android/components/sample/
├── MainActivity.kt               # Main entry point
├── MainViewModel.kt              # UI logic and state management
├── SampleApp.kt                  # Application class (Hilt setup)
├── factory/
│   └── FlowComponent.kt          # Payment component factory
├── ui/
│   ├── DemoScreen.kt             # Main UI composable
│   ├── SettingsScreen.kt         # Settings UI composables
│   ├── components/               # Reusable UI components
│   ├── model/                    # UI state and configuration classes
│   └── theme/                    # Material Design theme
├── extension/                    # Extension functions
└── repository/                   # Data layer (API calls, etc.)
```

### Technology Stack

| Technology               | Purpose              | Version    |
|--------------------------|----------------------|------------|
| **Kotlin**               | Programming language | 2.0.0+     |
| **Jetpack Compose**      | Modern UI framework  | 2025.08.01 |
| **Material 3**           | Design system        | 1.4.0      |
| **Hilt**                 | Dependency injection | 2.59.2     |
| **Coroutines**           | Async programming    | 1.10.2+    |
| **Retrofit**             | HTTP client          | 3.0.0      |
| **OkHttp**               | Network layer        | 5.3.2      |
| **Kotlin Serialization** | JSON serialization   | 1.10.0     |
| **Spotless**             | Code formatting      | 8.1.0      |


## Building and Deployment

### Build Variants

The project supports two build types:

**Debug Build** (Development)
- Minification disabled
- Full logging enabled
- No ProGuard/R8 optimization

**Release Build** (Production)
- Minification enabled with ProGuard rules
- Optimized for performance
- Production-ready security

### Building from Command Line

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Build and install on device
./gradlew installDebug
```

---

## Troubleshooting

### Common Issues

#### Issue: "SDK location not found"

**Solution**: Create/update `local.properties`:
```bash
echo "sdk.dir=$(dirname $(dirname $(which adb)))" > local.properties
```

#### Issue: "BuildConfig: PUBLIC_KEY not found"

**Solution**: Ensure `local.properties` contains all required keys:
```properties
sandbox.components.public_key=your_key_here
sandbox.components.secret_key=your_key_here
sandbox.components.processing_channel_id=your_key_here

production.components.public_key=your_key_here
production.components.secret_key=your_key_here
production.components.processing_channel_id=your_key_here
```

#### Issue: "Gradle sync failed"

**Solution**:
1. Click **File** → **Invalidate Caches**
2. Select **Invalidate and Restart**
3. Retry Gradle sync

#### Issue: "Module not found: checkout-android-components"

**Solution**: Ensure the library is properly published. Check `libs.versions.toml` for correct version:
```toml
checkoutAndroidComponents = "1.6.0"
```

---

## Support

**Documentation**: [Flow for mobile library reference](https://www.checkout.com/docs/payments/accept-payments/accept-a-payment-on-your-mobile-app/flow-for-mobile-library-reference/android)

---
## License
This project is licensed under the [MIT License](https://github.com/checkout/checkout-android-components/blob/main/LICENSE.txt).
