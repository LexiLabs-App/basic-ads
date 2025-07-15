package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.RewardedInterstitialAdHandler

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
@Composable
public fun rememberRewardedInterstitialAd(
    activity: Any?,
    adUnitId: String = AdUnitId.REWARDED_INTERSTITIAL_DEFAULT,
    onLoad: () -> Unit = {},
    onFailure: (Exception) -> Unit = {}
): MutableState<RewardedInterstitialAdHandler> {
    val ad = remember(activity) { mutableStateOf(RewardedInterstitialAdHandler(activity)) }
    when(ad.value.state){
        AdState.DISMISSED,
        AdState.NONE -> {
            ad.value.load(
                adUnitId = adUnitId,
                onLoad = onLoad,
                onFailure = onFailure
            )
        }
        else -> { /** DO NOTHING **/ }
    }
    return ad
}