package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.AdSize
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.InterstitialAd
import app.lexilabs.basic.ads.RewardedAd
import app.lexilabs.basic.ads.RewardedInterstitialAd

/**
 * Loads and displays a Banner Ad using a [Composable].
 * @param adUnitId Your AdMob AdUnitId [String]
 * @param adSize Your AdMob [AdSize]
 * @param onLoad Lambda expression that executes after the Google AdRequest has fully loaded
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
 * @param onLoad Lambda expression that executes after the [InterstitialAd] has fully loaded
 * @see AdUnitId.autoSelect
 */
@DependsOnGoogleMobileAds
@Composable public fun InterstitialAd(
    activity: Any?,
    adUnitId: String = AdUnitId.INTERSTITIAL_DEFAULT,
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
    onLoad: () -> Unit = {}
) {
    val ad by rememberInterstitialAd(activity)
    ad.load(
        adUnitId = adUnitId,
        onLoad = onLoad,
        onFailure = onFailure
    )
    ad.setListeners(
        onFailure = onFailure,
        onDismissed = onDismissed,
        onShown = onShown,
        onImpression = onImpression,
        onClick = onClick
    )
    ad.show()
}

/**
 * Displays a pre-loaded Interstitial Ad using a [Composable].
 * @param loadedAd an [InterstitialAd] pre-loaded before the call
 * @param onDismissed Lambda that executes when the user closes the ad
 * @param onShown Lambda expression that executes after the ad is presented
 * @param onImpression Lambda expression that executes after the user has seen the ad
 * @param onClick Lambda expression that executes after the user clicks the ad
 * @param onFailure Lambda expression that executes after the ad fails to load or redirect
 * @see AdUnitId.autoSelect
 * @see InterstitialAd.load
 */
@DependsOnGoogleMobileAds
@Composable public fun InterstitialAd(
    loadedAd: InterstitialAd,
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
) {
    loadedAd.setListeners(
        onFailure = onFailure,
        onDismissed = onDismissed,
        onShown = onShown,
        onImpression = onImpression,
        onClick = onClick
    )
    loadedAd.show()
}

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
 * @param onLoad Lambda expression that executes after the [InterstitialAd] has fully loaded
 * @see AdUnitId.autoSelect
 */
@DependsOnGoogleMobileAds
@Composable public fun RewardedAd(
    activity: Any?,
    adUnitId: String = AdUnitId.REWARDED_DEFAULT,
    onRewardEarned: () -> Unit,
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
    onLoad: () -> Unit = {}
) {
    val ad by rememberRewardedAd(activity)
    ad.load(
        adUnitId = adUnitId,
        onLoad = onLoad,
        onFailure = onFailure
    )
    ad.setListeners(
        onFailure = onFailure,
        onDismissed = onDismissed,
        onShown = onShown,
        onImpression = onImpression,
        onClick = onClick
    )
    ad.show(
        onRewardEarned = onRewardEarned
    )
}

/**
 * Displays a pre-loaded Rewarded Ad using a [Composable].
 * @param loadedAd an [RewardedAd] pre-loaded before the call
 * @param onRewardEarned Lambda that executes when the user has earned an ad-related reward
 * @param onDismissed Lambda that executes when the user closes the ad
 * @param onShown Lambda expression that executes after the ad is presented
 * @param onImpression Lambda expression that executes after the user has seen the ad
 * @param onClick Lambda expression that executes after the user clicks the ad
 * @param onFailure Lambda expression that executes after the ad fails to load or redirect
 * @see AdUnitId.autoSelect
 * @see RewardedAd.load
 */
@DependsOnGoogleMobileAds
@Composable public fun RewardedAd(
    loadedAd: RewardedAd,
    onRewardEarned: () -> Unit,
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
) {
    loadedAd.setListeners(
        onFailure = onFailure,
        onDismissed = onDismissed,
        onShown = onShown,
        onImpression = onImpression,
        onClick = onClick
    )
    loadedAd.show(
        onRewardEarned = onRewardEarned
    )
}

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
 * @param onLoad Lambda expression that executes after the [InterstitialAd] has fully loaded
 * @see AdUnitId.autoSelect
 */
@DependsOnGoogleMobileAds
@Composable public fun RewardedInterstitialAd(
    activity: Any?,
    adUnitId: String = AdUnitId.REWARDED_INTERSTITIAL_DEFAULT,
    onRewardEarned: () -> Unit,
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
    onLoad: () -> Unit = {}
) {
    val ad by rememberRewardedAd(activity)
    ad.load(
        adUnitId = adUnitId,
        onLoad = onLoad,
        onFailure = onFailure
    )
    ad.setListeners(
        onFailure = onFailure,
        onDismissed = onDismissed,
        onShown = onShown,
        onImpression = onImpression,
        onClick = onClick
    )
    ad.show(
        onRewardEarned = onRewardEarned
    )
}

/**
 * Displays a pre-loaded Rewarded Interstitial Ad using a [Composable].
 * @param loadedAd an [RewardedInterstitialAd] pre-loaded before the call
 * @param onRewardEarned Lambda that executes when the user has earned an ad-related reward
 * @param onDismissed Lambda that executes when the user closes the ad
 * @param onShown Lambda expression that executes after the ad is presented
 * @param onImpression Lambda expression that executes after the user has seen the ad
 * @param onClick Lambda expression that executes after the user clicks the ad
 * @param onFailure Lambda expression that executes after the ad fails to load or redirect
 * @see AdUnitId.autoSelect
 */
@DependsOnGoogleMobileAds
@Composable public fun RewardedInterstitialAd(
    loadedAd: RewardedInterstitialAd,
    onRewardEarned: () -> Unit,
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
) {
    loadedAd.setListeners(
        onFailure = onFailure,
        onDismissed = onDismissed,
        onShown = onShown,
        onImpression = onImpression,
        onClick = onClick
    )
    loadedAd.show(
        onRewardEarned = onRewardEarned
    )
}

@DependsOnGoogleMobileAds
@Composable
public fun rememberInterstitialAd(activity: Any?): MutableState<InterstitialAd> =
    remember(activity) { mutableStateOf(InterstitialAd(activity)) }

@DependsOnGoogleMobileAds
@Composable
public fun rememberRewardedAd(activity: Any?): MutableState<RewardedAd> =
    remember(activity) { mutableStateOf(RewardedAd(activity)) }

@DependsOnGoogleMobileAds
@Composable
public fun rememberRewardedInterstitialAd(activity: Any?): MutableState<RewardedInterstitialAd> =
    remember(activity) { mutableStateOf(RewardedInterstitialAd(activity)) }