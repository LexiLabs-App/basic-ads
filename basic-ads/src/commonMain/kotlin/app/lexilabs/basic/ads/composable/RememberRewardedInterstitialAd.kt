package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.RewardedInterstitialAdHandler

@DependsOnGoogleMobileAds
@Composable
public fun rememberRewardedInterstitialAd(
    activity: Any?,
    adUnitId: String = AdUnitId.REWARDED_INTERSTITIAL_DEFAULT,
    onLoad: () -> Unit = {},
    onFailure: (Exception) -> Unit = {}
): MutableState<RewardedInterstitialAdHandler> {
    val ad = remember(activity) { mutableStateOf(RewardedInterstitialAdHandler(activity)) }
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