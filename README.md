# Basic-Ads
<img src="images/logo-icon.svg" alt="basic" height="240" align="right"/> 

![GitHub License](https://img.shields.io/github/license/lexilabs-app/basic-ads)
![GitHub Release Date](https://img.shields.io/github/release-date/lexilabs-app/basic-ads)
[![Latest Release](https://img.shields.io/maven-central/v/app.lexilabs.basic/basic-ads?color=blue&label=latest)](https://central.sonatype.com/artifact/app.lexilabs.basic/basic-ads)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.21-7f52ff.svg?style=flat&logo=kotlin)](https://kotlinlang.org)

A Kotlin Multiplatform library to rapidly get Google AdMob running on Android and iOS

![badge-android](http://img.shields.io/badge/android-full_support-65c663.svg?style=flat)
![badge-ios](http://img.shields.io/badge/ios-full_support-65c663.svg?style=flat)

### How it works
Basic-Ads uses the existing Android and iOS [Google AdMob](https://admob.google.com/) libraries to display ads as `Composables`.
A [full walkthrough](https://medium.com/@robert.jamison/composable-ads-f8795924aa0d) is available on Medium,
and there's also [an easy-start template](https://github.com/LexiLabs-App/Example-Basic-Ads).

In case you need it, here's the [Basic-Ads API Documentation](https://ads.basic.lexilabs.app)

## Preparation
For **Android**, complete the steps in AdMob's instructions:

* [Configure your app](https://developers.google.com/admob/android/quick-start#import_the_mobile_ads_sdk)

For **iOS**, complete the steps in AdMob's instructions:

* [Import the Mobile Ads SDK](https://developers.google.com/admob/ios/quick-start#import_the_mobile_ads_sdk)

* [For GDPR Compliance Only] [Import the User Messaging Platform SDK](https://developers.google.com/admob/ios/privacy)

* [Update your Info.plist](https://developers.google.com/admob/ios/quick-start#update_your_infoplist)

***NOTE: For Xcode 13+, you can update your [Custom iOS Target Properties](https://useyourloaf.com/blog/xcode-13-missing-info.plist/).***

## Installation
* [![Stable Release](https://img.shields.io/github/v/release/LexiLabs-App/basic-ads?filter=!*.*.*-*&label=stable&color=65c663)](https://central.sonatype.com/artifact/app.lexilabs.basic/basic-ads)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.21-7f52ff.svg?style=flat&logo=kotlin)](https://kotlinlang.org)

* [![Latest Release](https://img.shields.io/maven-central/v/app.lexilabs.basic/basic-ads?color=yellow&label=latest)](https://central.sonatype.com/artifact/app.lexilabs.basic/basic-ads)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.21-7f52ff.svg?style=flat&logo=kotlin)](https://kotlinlang.org)

Don't forget to [check the list of transitive dependencies and versions](VERSIONS.md) to ensure compatibility.

Add your dependencies from Maven
```toml
# in your 'libs.versions.toml' file
[versions]
kotlin = "+" # gets the latest version
compose = "+" # gets the latest version
basic = "+" # gets the latest version
google-play-services-ads = "+" # you did this during the preparation step
android-ump = "+" # you did this during the preparation step

[libraries]
basic-ads = { module = "app.lexilabs.basic:basic-ads", version.ref = "basic"}
google-play-services-ads = { module = "com.google.android.gms:play-services-ads", version.ref = "google-play-services-ads"}
android-ump = { module = "com.google.android.ump:user-messaging-platform", version.ref = "android-ump" }

[plugins] # make sure you're using the JetBrains plugin to import your composables
jetbrainsCompose = { id = "org.jetbrains.compose", version.ref = "compose" }
compose-compiler = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
```

then include the library in your gradle build
```kotlin
// in your 'composeApp/build.gradle.kts' file
plugins {
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

sourceSets {
    commonMain.dependencies {
        implementation(compose.runtime)
        implementation(compose.foundation)
        implementation(compose.material)
        implementation(compose.ui)
        implementation(libs.lexilabs.basic.ads)
    }
    androidMain.dependencies {
        implementation(libs.google.play.services.ads)
        implementation(libs.android.ump)
    }
}
```

## Initialization
Call `BasicAds.initialize` in your `commonMain` before building ads.
***NOTE: You do not need to initialize within each platform.***

```kotlin
// in your 'composeApp/src/commonMain/App.kt' file
@OptIn(DependsOnGoogleMobileAds::class)
@Composable
fun App() {
    // You'll need to access your platform-specific context (Android) or null (iOS) to pass as an `Any?` argument
    BasicAds.initialize(
        platformContext
    )
    //...
}
```

## Composing a `BannerAd`
You can build a `BannerAd` via a `Composable` function:
_NOTE: If you want the [deprecated Composables](DEPRECATED.md), they'll be included until version 0.2.7_
```kotlin
// in your 'composeApp/src/commonMain/AdScreen.kt' file
@Composable
fun AdScreen() {
    BannerAd() // Results in a Test Banner Ad being created
}
```

## Creating a `RewardedAd` or `InterstitialAd`

You can also build other Ad types in your `ViewModel`, 
but you'll need to [pass your Android `Activity` `Context` when you initialize](https://blog.hakz.com/contain-your-apps-memory-please-0c62819f8d7f).

```kotlin
// in your 'composeApp/src/commonMain/AdViewModel.kt' file
// You'll need to access your platform-specific Activity (Android) or null (iOS) to pass as an `Any?` argument
class AdViewModel(activity: Any?): ViewModel() {
    val rewardedAd = RewardedAd(activity)
    val interstitialAd = InterstitialAd(activity)
    val rewardedInterstitialAd = RewardedInterstitialAd(activity) // currently a Google Beta feature
    /*...*/
}

```
You'll want to load each ad
```kotlin
rewardedInterstitial.load(
    adUnitId = "Ad Unit ID goes here",
    onLoad = {
        // Some people call setListeners() here...
    },
    onFailure = { Log.e(tag, "${it.message}")}
)
```

Don't forget to set listeners to observe user actions with the ad:
```kotlin
rewarded.setListeners(
    onFailure = { Log.e(tag, "${it.message}") },
    onDismissed = { 
        loadRewardedAd() // I like to load the next add during this phase
    }
)
```

Lastly, show the ad:
```kotlin
rewarded.show { onRewardEarned() }
```

## [FOR GDPR COMPLIANCE ONLY] Consent Requests

This topic can go _very_ in-depth, so please begin by reading about [what GDPR is](https://gdpr.eu/) and [how AdMob complies with GDPR requirements](https://support.google.com/admob/answer/7666366?hl=en).

Once you're familiar with the consent banner requirements, feel free to begin using the `Consent` features of Basic Ads:
```kotlin
// in your 'composeApp/src/commonMain/AdScreen.kt' file
val consentInfo = Consent(activity) // Create a Consent object

// Check what the app's consent requirements are
consentInfo.requestConsentInfoUpdate(
    onError = { error: Exception ->
        Log.e(tag, error.message)
    }
)

// Show the consent form
consentInfo.loadAndShowConsentForm(
    onError = { error: Exception ->
        Log.e(tag, error.message)
    }
)

// Check if privacy options are required
if (consentInfo.isPrivacyOptionsRequired()) {
    // Load and present the privacy form
    consentInfo.showPrivacyOptionsForm(
        onDismissed = {
            Log.d(tag, "dismissed")
        },
        onError = { error: Exception ->
            Log.e(tag, error.message)
        }
    )
} 

// Check if the user can see ads
if (consentInfo.canRequestAds()) {
    /** Logic to show your ads **/
    AdScreen()
}
```

### \[Advanced Users Only\] How to deal with building this garbage
1. Find a large cup. It must exist in the real world.
2. Fill said cup to the brim with some sort of caffeinated beverage.
3. Click `File` > `Invalidate Caches...`, check all boxes and hit `invalidate and restart`
4. Click `Sync` for gradle if banner exists. Ignore the flood of warning lights and klaxons.
5. Click `Build` > `Clean Cache`.  Ignore the plethora of errors
6. Once complete, click `Build` > `Rebuild Project`. NOTE: Despite religious preference, prayer is encouraged.

### Known Issues:
* [Doesn't support Native Ads (yet)](https://github.com/LexiLabs-App/basic-ads/issues/29)
