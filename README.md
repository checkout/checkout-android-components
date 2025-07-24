![Checkout.com](./.github/media/checkout.com.logo.png)
![Checkout.com](./.github/media/flow-logo.png)

#  Flow for Mobile Android SDK

![license](https://img.shields.io/github/license/checkout/checkout-android-components.svg)
![GitHub release (latest by date)](https://img.shields.io/github/v/release/checkout/checkout-android-components?label=maven-central)

- [Integration](#integration)
- [Releases](#releases)

## Integration

Use Gradle to import the SDK into your app.

In your project-level `build.gradle` file, add:

```
repositories {
    mavenCentral()

// Ensure the following Maven repositories are included, as they are specifically required for resolving Risk SDK dependencies:
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://maven.fpregistry.io/releases") }
}
```

In your app-level build.gradle file, add:

```
dependencies {
    implementation 'com.checkout:checkout-android-components:$latest_version'
}
```

For detailed integration steps, refer to our 
<kbd>[official documentation](https://www.checkout.com/docs/payments/accept-payments/accept-a-payment-on-your-mobile-app)  ↗️ </kbd>. 

## Releases
Find our <kbd>[Release Note](https://github.com/checkout/checkout-android-components/releases) ↗️</kbd>

