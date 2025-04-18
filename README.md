# Basic-Ads
<img src="images/logo-icon.svg" alt="basic" height="240" align="right"/> 

![GitHub License](https://img.shields.io/github/license/lexilabs-app/basic-ads)
![GitHub Release Date](https://img.shields.io/github/release-date/lexilabs-app/basic-ads)

[![Latest Stable Release](https://img.shields.io/github/v/release/LexiLabs-App/basic-ads?filter=!*.*.*-*&label=stable&color=65c663)](https://central.sonatype.com/artifact/app.lexilabs.basic/basic-ads)
[![Maven Central](https://img.shields.io/maven-central/v/app.lexilabs.basic/basic-ads?color=blue)](https://central.sonatype.com/artifact/app.lexilabs.basic/basic-ads)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.20-7f52ff.svg?style=flat&logo=kotlin)](https://kotlinlang.org)

A Kotlin Multiplatform library to rapidly get Google AdMob running on Android and iOS

![badge-android](http://img.shields.io/badge/android-full_support-65c663.svg?style=flat)
![badge-ios](http://img.shields.io/badge/ios-full_support-65c663.svg?style=flat)
![badge-wasm](https://img.shields.io/badge/wasm-compile_only-yellow.svg?style=flat)
![badge-wasm](https://img.shields.io/badge/wasm-compile_only-yellow.svg?style=flat)

### How it works
Basic-Ads uses the existing Android and iOS Google AdMob libraries to display ads as `Composables`.
A [full walkthrough](https://medium.com/@robert.jamison/composable-ads-f8795924aa0d) is available on my Medium Blog,
and there's also [an easy-start template](https://github.com/LexiLabs-App/Example-Basic-Ads).

## Preparation
For **Android**, complete the steps in AdMob's instructions:

* [Configure your app](https://developers.google.com/admob/android/quick-start#import_the_mobile_ads_sdk)

For **iOS**, complete the steps in AdMob's instructions:

* [Import the Mobile Ads SDK](https://developers.google.com/admob/ios/quick-start#import_the_mobile_ads_sdk)

* [Update your Info.plist](https://developers.google.com/admob/ios/quick-start#update_your_infoplist)

***NOTE: For Xcode 13+, you can update your [Custom iOS Target Properties](https://useyourloaf.com/blog/xcode-13-missing-info.plist/).***

## Installation
Add your dependencies from Maven
```toml
# in your 'libs.versions.toml' file
[versions]
kotlin = "+" # gets the latest version
compose = "+" # gets the latest version
basic = "+" # gets the latest version
google-play-services-ads = "+" # you did this during the preparation step

[libraries]
basic-ads = { module = "app.lexilabs.basic:basic-ads", version.ref = "basic"}
basic-logging = { module = "app.lexilabs.basic:basic-logging", version.ref = "basic"}
google-play-services-ads = { module = "com.google.android.gms:play-services-ads", version.ref = "google-play-services-ads"}

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
        implementation(libs.lexilabs.basic.ads)
        implementation(libs.google.play.services.ads)
    }
}
```

## Usage
Call [BasicAds.initialize] in your `commonMain` before building ads.
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

Now you can build ads:

```kotlin
// in your 'composeApp/src/commonMain/AdScreen.kt' file
@Composable
fun AdScreen() {
    BannerAd() // Results in a Test Banner Ad being created
    // You'll need to access your platform-specific Activity (Android) or null (iOS) to pass as an `Any?` argument
    InterstitialAd(activity) // Results in a Test Interstitial Ad being created
    RewardedInterstitialAd(activity, {} ) // Results in a Test Rewarded Interstitial Ad (Beta) being created
    RewardedAd(activity, {}) // Results in a Test Rewarded Ad being created
}
```

If you want to customize your ad experience, you can take advantage of lambdas:
```kotlin
// in your 'composeApp/src/commonMain/AdScreen.kt' file
@Composable
fun AdScreen() {
    // You'll need to access your platform-specific Activity (Android) or null (iOS) to pass as an `Any?` argument
    RewardedAd(
        activity = activity,
        adUnitId = AdUnitId.autoSelect(
            "YOUR_ANDROID_AD_UNIT_ID",
            "YOUR_IOS_AD_UNIT_ID"
        ),
        onDismissed = {
            doSomethingElse()
        },
        onRewardEarned = {
            playSomeCoolSound()
        },
        onShown = {
            addValueToSomeCounter()
        },
        onImpression = {
            addValueToSomeTracker()
        },
        onClick = {
            incrementSomeValueSomewhere()
        },
        onFailure = {
            runTheBackupOption()
        }
    )
}
```

In case you need it, here's some [additional documentation](https://basic.lexilabs.app/basic-ads)

## Versioning

Here's a list of the dependency versions for each release after 0.2.0:

| Basic-Ads<br/>Version |   Kotlin    | Compose<br/>Foundation | Annotations | AdMob<br/>Android | AdMob<br/>iOS |
|:---------------------:|:-----------:|:----------------------:|:-----------:|:-----------------:|:-------------:|
|         0.2.0         | 2.1.0-Beta1 |       1.7.0-rc01       |    1.8.2    |      23.4.0       |    11.9.0     |
|         0.2.1         |  2.1.0-RC2  |         1.7.0          |    1.9.1    |      23.5.0       |    11.9.0     |
|         0.2.2         |    2.1.0    |         1.7.1          |    1.9.1    |      23.5.0       |    11.9.0     |
|         0.2.3         |   2.0.21    |         1.7.1          |    1.9.1    |      23.6.0       |    11.9.0     |
|         0.2.4         |   2.0.21    |         1.7.1          |    1.9.1    |      23.6.0       |    11.9.0     |
|         0.2.5         |   2.1.10    |         1.7.3          |    1.9.1    |      24.0.0       |    12.1.0     |
|     0.2.6-Beta01      |   2.1.20    |         1.7.3          |    1.9.1    |      24.1.0       |    12.2.0     |
|     0.2.6-beta02      |   2.1.20    |         1.7.3          |    1.9.1    |      24.2.0       |    12.2.0     |
|     0.2.6-beta03      |   2.1.20    |         1.7.3          |    1.9.1    |      24.2.0       |    12.2.0     |
|     0.2.6-beta04      |  2.1.21-RC  |         1.7.3          |    1.9.1    |      24.2.0       |    12.2.0     |

### \[Advanced Users Only\] How to deal with building this garbage
1. Find a large cup. It must exist in the real world.
2. Fill said cup to the brim with some sort of caffeinated beverage.
3. Click `File` > `Invalidate Caches...`, check all boxes and hit `invalidate and restart`
4. Click `Sync` for gradle if banner exists. Ignore the flood of warning lights and klaxons.
5. Click `Build` > `Clean Cache`.  Ignore the plethora of errors
6. Once complete, click `Build` > `Rebuild Project`. NOTE: Despite religious preference, prayer is encouraged.

### Known Issues:
* [Can't compile using Xcode 16.3 due to breaking changes](https://youtrack.jetbrains.com/issue/KT-76460) ***FIXED IN 0.2.6-beta04+***
