package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.nativead.NativeAdHandler

/**
 * Remembers a [NativeAdHandler] across compositions.
 * @param adUnitId Your AdMob AdUnitId [String]
 * @param onLoad Lambda expression that executes after the [NativeAdHandler] has fully loaded
 * @param onFailure Lambda expression that executes after the ad fails to load or redirect
 * @param onDismissed Lambda that executes when the user closes the ad
 * @param onShown Lambda expression that executes after the ad is presented
 * @param onImpression Lambda expression that executes after the user has seen the ad
 * @param onClick Lambda expression that executes after the user clicks the ad
 * @return a [MutableState] of [NativeAdHandler]
 * @see AdUnitId.autoSelect
 */
@DependsOnGoogleMobileAds
@Composable
public actual fun rememberNativeAd(
    adUnitId: String,
    onLoad: () -> Unit,
    onFailure: (Exception) -> Unit,
    onDismissed: () -> Unit,
    onShown: () -> Unit,
    onImpression: () -> Unit,
    onClick: () -> Unit,
): MutableState<NativeAdHandler> {
    val ad = remember(null) { mutableStateOf(NativeAdHandler(null)) }
    DisposableEffect(ad) {
        onDispose {
            ad.value.destroy()
        }
    }
    when(ad.value.state){
        AdState.DISMISSED,
        AdState.NONE -> {
            ad.value.load(
                adUnitId = adUnitId,
                onLoad = onLoad,
                onFailure = onFailure,
                onDismissed = onDismissed,
                onShown = onShown,
                onImpression = onImpression,
                onClick = onClick
            )
        }
        else -> { /** DO NOTHING **/ }
    }
    return ad
}