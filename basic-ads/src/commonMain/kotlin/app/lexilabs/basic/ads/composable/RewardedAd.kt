package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.InterstitialAdHandler
import app.lexilabs.basic.ads.RewardItem
import app.lexilabs.basic.ads.RewardedAdHandler

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
 * @param onLoad Lambda expression that executes after the [InterstitialAdHandler] has fully loaded
 * @see AdUnitId.autoSelect
 */
@DependsOnGoogleMobileAds
@Composable
public fun RewardedAd(
    activity: Any?,
    adUnitId: String = AdUnitId.REWARDED_DEFAULT,
    userId: String? = null,
    customData: String? = null,
    onRewardEarned: (RewardItem) -> Unit,
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
    onLoad: () -> Unit = {}
) {
    val ad by rememberRewardedAd(
        activity = activity,
        adUnitId = adUnitId,
        userId = userId,
        customData = customData,
        onLoad = onLoad,
        onFailure = onFailure
    )
    if (ad.state == AdState.READY) {
        ad.setListeners(
            onFailure = onFailure,
            onDismissed = onDismissed,
            onShown = onShown,
            onImpression = onImpression,
            onClick = onClick
        )
        ad.show { onRewardEarned(it) }
    }
}

/**
 * Displays a pre-loaded Rewarded Ad using a [Composable].
 * @param loadedAd an [RewardedAdHandler] pre-loaded before the call
 * @param onRewardEarned Lambda that executes when the user has earned an ad-related reward
 * @param onDismissed Lambda that executes when the user closes the ad
 * @param onShown Lambda expression that executes after the ad is presented
 * @param onImpression Lambda expression that executes after the user has seen the ad
 * @param onClick Lambda expression that executes after the user clicks the ad
 * @param onFailure Lambda expression that executes after the ad fails to load or redirect
 * @see AdUnitId.autoSelect
 * @see RewardedAdHandler.load
 */
@DependsOnGoogleMobileAds
@Composable
public fun RewardedAd(
    loadedAd: RewardedAdHandler,
    onRewardEarned: (RewardItem) -> Unit,
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
    loadedAd.show { onRewardEarned(it) }
}
