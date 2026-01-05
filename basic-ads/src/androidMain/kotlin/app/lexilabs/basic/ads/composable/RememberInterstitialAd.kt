package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.InterstitialAdHandler
import app.lexilabs.basic.ads.getActivity

/**
 * Composable function to remember an interstitial ad.
 *
 * This function creates and manages an [InterstitialAdHandler] instance,
 * automatically loading an ad when the state is [AdState.DISMISSED] or [AdState.NONE].
 *
 * @param adUnitId The ad unit ID for the interstitial ad. Defaults to [AdUnitId.INTERSTITIAL_DEFAULT].
 * @param onLoad A callback function invoked when the ad is successfully loaded.
 * @param onFailure A callback function invoked when ad loading fails, providing the [Exception] that occurred.
 * @return A [MutableState] holding the [InterstitialAdHandler]. You can use this to control and observe the ad's state (e.g., to show the ad).
 */
@DependsOnGoogleMobileAds
@Composable
public actual fun rememberInterstitialAd(
    adUnitId: String,
    onLoad: () -> Unit,
    onFailure: (Exception) -> Unit
): MutableState<InterstitialAdHandler> {
    val activity = LocalContext.current.getActivity()
    val ad = remember(activity) { mutableStateOf(InterstitialAdHandler(activity)) }
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

/**
 * Composable function to remember an interstitial ad.
 *
 * This function creates and manages an [InterstitialAdHandler] instance,
 * automatically loading an ad when the state is [AdState.DISMISSED] or [AdState.NONE].
 *
 * @param activity The activity context, required for ad loading.
 * @param adUnitId The ad unit ID for the interstitial ad. Defaults to [AdUnitId.INTERSTITIAL_DEFAULT].
 * @param onLoad A callback function invoked when the ad is successfully loaded.
 * @param onFailure A callback function invoked when ad loading fails, providing the [Exception] that occurred.
 * @return A [MutableState] holding the [InterstitialAdHandler]. You can use this to control and observe the ad's state (e.g., to show the ad).
 */
@DependsOnGoogleMobileAds
@Deprecated("The `activity` argument is no longer required as of v1.1.0-beta01")
@Composable
public actual fun rememberInterstitialAd(
    activity: Any?,
    adUnitId: String,
    onLoad: () -> Unit,
    onFailure: (Exception) -> Unit
): MutableState<InterstitialAdHandler> {
    val ad = remember(activity) { mutableStateOf(InterstitialAdHandler(activity)) }
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