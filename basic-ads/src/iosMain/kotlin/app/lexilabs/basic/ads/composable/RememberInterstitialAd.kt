package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.InterstitialAdHandler

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
    val ad = remember(null) { mutableStateOf(InterstitialAdHandler(null)) }
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