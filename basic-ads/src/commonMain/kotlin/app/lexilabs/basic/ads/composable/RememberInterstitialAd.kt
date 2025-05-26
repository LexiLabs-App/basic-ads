package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.InterstitialAdHandler

@DependsOnGoogleMobileAds
@Composable
public fun rememberInterstitialAd(
    activity: Any?,
    adUnitId: String = AdUnitId.INTERSTITIAL_DEFAULT,
    onLoad: () -> Unit = {},
    onFailure: (Exception) -> Unit = {}
): MutableState<InterstitialAdHandler> {
    val ad = remember(activity) { mutableStateOf(InterstitialAdHandler(activity)) }
    ad.value.load(
        adUnitId = adUnitId,
        onLoad = onLoad,
        onFailure = onFailure
    )
    if (ad.value.state == AdState.DISMISSED){
        ad.value.load(
            adUnitId = adUnitId,
            onLoad = onLoad,
            onFailure = onFailure
        )
    }
    return ad
}