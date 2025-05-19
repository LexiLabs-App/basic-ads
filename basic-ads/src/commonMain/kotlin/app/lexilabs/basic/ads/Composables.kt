package app.lexilabs.basic.ads

import androidx.compose.runtime.Composable

/**
 * Loads and displays a Banner Ad using a [Composable].
 * @param adUnitId Your AdMob AdUnitId [String]
 * @param adSize Your AdMob [AdSize]
 * @param onLoad Lambda expression that executes after the [AdRequest] has fully loaded
 * @see AdUnitId.autoSelect
 */
@DependsOnGoogleMobileAds
@Composable public expect fun BannerAd(
    adUnitId: String = AdUnitId.BANNER_DEFAULT,
    adSize: AdSize = AdSize.FULL_BANNER,
    onLoad: () -> Unit = {}
)

/**
 * Loads and displays an Interstitial Ad using a [Composable].
 * @param activity the current Activity (only needed for Android Impl)
 * @param adUnitId Your AdMob AdUnitId [String]
 * @param onDismissed Lambda that executes when the user closes the ad
 * @param onShown Lambda expression that executes after the ad is presented
 * @param onImpression Lambda expression that executes after the user has seen the ad
 * @param onClick Lambda expression that executes after the user clicks the ad
 * @param onFailure Lambda expression that executes after the ad fails to load or redirect
 * @param onLoad Lambda expression that executes after the [AdRequest] has fully loaded
 * @see AdUnitId.autoSelect
 */
@Deprecated(
    message = "Will be replaced by InterstitialAd class in Basic-Ads version 0.2.7",
    replaceWith = ReplaceWith(
        expression = "InterstitialAd(activity).load(adUnitId, onLoad, onFailure)",
        imports = arrayOf("app.lexilabs.basic.ads.InterstitialAd")
    ),
    level = DeprecationLevel.WARNING
)
@DependsOnGoogleMobileAds
@Composable public expect fun InterstitialAd(
    activity: Any?,
    adUnitId: String = AdUnitId.INTERSTITIAL_DEFAULT,
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
    onFailure: () -> Unit = {},
    onLoad: () -> Unit = {}
)

/**
 * Loads and displays a Rewarded Ad using a [Composable].
 * @param activity the current Activity (only needed for Android Impl)
 * @param adUnitId Your AdMob AdUnitId [String]
 * @param onRewardEarned Lambda that executes when the user has earned an ad-related reward
 * @param onDismissed Lambda that executes when the user closes the ad
 * @param onShown Lambda expression that executes after the ad is presented
 * @param onImpression Lambda expression that executes after the user has seen the ad
 * @param onClick Lambda expression that executes after the user clicks the ad
 * @param onFailure Lambda expression that executes after the ad fails to load or redirect
 * @param onLoad Lambda expression that executes after the [AdRequest] has fully loaded
 * @see AdUnitId.autoSelect
 */
@Deprecated(
    message = "Will be replaced by RewardedAd class in Basic-Ads version 0.2.7",
    replaceWith = ReplaceWith(
        expression = "RewardedAd(activity).load(adUnitId, onLoad, onFailure)",
        imports = arrayOf("app.lexilabs.basic.ads.RewardedAd")
    ),
    level = DeprecationLevel.WARNING
)
@DependsOnGoogleMobileAds
@Composable public expect fun RewardedAd(
    activity: Any?,
    adUnitId: String = AdUnitId.REWARDED_DEFAULT,
    onRewardEarned: () -> Unit,
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
    onFailure: () -> Unit = {},
    onLoad: () -> Unit = {}
)

/**
 * Loads and displays a Rewarded Interstitial Ad using a [Composable].
 * @param activity the current Activity (only needed for Android Impl)
 * @param adUnitId Your AdMob AdUnitId [String]
 * @param onRewardEarned Lambda that executes when the user has earned an ad-related reward
 * @param onDismissed Lambda that executes when the user closes the ad
 * @param onShown Lambda expression that executes after the ad is presented
 * @param onImpression Lambda expression that executes after the user has seen the ad
 * @param onClick Lambda expression that executes after the user clicks the ad
 * @param onFailure Lambda expression that executes after the ad fails to load or redirect
 * @param onLoad Lambda expression that executes after the [AdRequest] has fully loaded
 * @see AdUnitId.autoSelect
 */
@Deprecated(
    message = "Will be replaced by RewardedInterstitialAd class in Basic-Ads version 0.2.7",
    replaceWith = ReplaceWith(
        expression = "RewardedInterstitialAd(activity).load(adUnitId, onLoad,onFailure)",
        imports = arrayOf("app.lexilabs.basic.ads.RewardedInterstitialAd")
    ),
    level = DeprecationLevel.WARNING
)
@DependsOnGoogleMobileAds
@Composable public expect fun RewardedInterstitialAd(
    activity: Any?,
    adUnitId: String = AdUnitId.REWARDED_INTERSTITIAL_DEFAULT,
    onRewardEarned: () -> Unit,
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
    onFailure: () -> Unit = {},
    onLoad: () -> Unit = {}
)