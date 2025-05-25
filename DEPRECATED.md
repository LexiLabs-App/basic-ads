# Deprecated Functions
The following functions are deprecated as of v0.2.6 and will be removed in v0.2.7 or v1.0.0 -- whichever is first.

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