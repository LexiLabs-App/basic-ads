package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.AdState.DISMISSED
import app.lexilabs.basic.ads.AdState.NONE
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.RewardedAdHandler
import app.lexilabs.basic.ads.RewardedInterstitialAdHandler

/**
 * A composable function that remembers and manages a RewardedInterstitialAdHandler.
 *
 * This function handles the lifecycle of a rewarded interstitial ad, including loading and reloading it
 * when it's dismissed or hasn't been loaded yet.
 *
 * @param adUnitId The ad unit ID for the rewarded interstitial ad. Defaults to [AdUnitId.REWARDED_INTERSTITIAL_DEFAULT].
 * @param onLoad A callback function that is invoked when the ad has successfully loaded.
 * @param onFailure A callback function that is invoked if the ad fails to load.
 *                  It provides an [Exception] object containing details about the failure.
 * @return A [MutableState] holding the [RewardedInterstitialAdHandler]. This allows you to interact
 *         with the ad (e.g., to show it) and observe its state.
 *
 * @see RewardedInterstitialAdHandler
 * @see AdUnitId.REWARDED_INTERSTITIAL_DEFAULT
 * @see DependsOnGoogleMobileAds
 */
@DependsOnGoogleMobileAds
@Composable
public expect fun rememberRewardedInterstitialAd(
    adUnitId: String = AdUnitId.REWARDED_INTERSTITIAL_DEFAULT,
    onLoad: () -> Unit = {},
    onFailure: (Exception) -> Unit = {}
): MutableState<RewardedInterstitialAdHandler>

/**
 * Remembers a [RewardedAdHandler], which is used to load and show rewarded ads.
 *
 * This function will automatically attempt to load an ad when the [RewardedAdHandler.state]
 * is [AdState.NONE] or [AdState.DISMISSED].
 *
 * @param userId Used for Server-Side Verification
 * @param customData Used for Server-Side Verification
 * @param adUnitId The ad unit ID to use for loading the ad. Defaults to [AdUnitId.REWARDED_DEFAULT].
 * @param onLoad A callback that will be invoked when the ad has successfully loaded.
 * @param onFailure A callback that will be invoked if the ad fails to load, providing an [Exception] with details of the failure.
 * @return A [MutableState] holding the [RewardedAdHandler]. You can observe this state to react to changes in the ad's lifecycle.
 */
@DependsOnGoogleMobileAds
@Composable
public expect fun rememberRewardedInterstitialAd(
    userId: String,
    customData: String,
    adUnitId: String = AdUnitId.REWARDED_DEFAULT,
    onLoad: () -> Unit = {},
    onFailure: (Exception) -> Unit = {}
): MutableState<RewardedAdHandler>

/**
 * A composable function that remembers and manages a RewardedInterstitialAdHandler.
 *
 * This function handles the lifecycle of a rewarded interstitial ad, including loading and reloading it
 * when it's dismissed or hasn't been loaded yet.
 *
 * @param activity The current activity context. This is crucial for initializing and displaying the ad.
 *                 It can be null, but ads will not function correctly if it is.
 * @param adUnitId The ad unit ID for the rewarded interstitial ad. Defaults to [AdUnitId.REWARDED_INTERSTITIAL_DEFAULT].
 * @param onLoad A callback function that is invoked when the ad has successfully loaded.
 * @param onFailure A callback function that is invoked if the ad fails to load.
 *                  It provides an [Exception] object containing details about the failure.
 * @return A [MutableState] holding the [RewardedInterstitialAdHandler]. This allows you to interact
 *         with the ad (e.g., to show it) and observe its state.
 *
 * @see RewardedInterstitialAdHandler
 * @see AdUnitId.REWARDED_INTERSTITIAL_DEFAULT
 * @see DependsOnGoogleMobileAds
 */
@DependsOnGoogleMobileAds
@Deprecated("The `activity` argument is no longer required as of v1.1.0-beta01")
@Composable
public expect fun rememberRewardedInterstitialAd(
    activity: Any?,
    adUnitId: String = AdUnitId.REWARDED_INTERSTITIAL_DEFAULT,
    onLoad: () -> Unit = {},
    onFailure: (Exception) -> Unit = {}
): MutableState<RewardedInterstitialAdHandler>