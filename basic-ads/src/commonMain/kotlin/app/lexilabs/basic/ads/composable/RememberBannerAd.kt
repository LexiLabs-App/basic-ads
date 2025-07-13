package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.AdSize
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.BannerAdHandler
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds

@DependsOnGoogleMobileAds
@Composable
public fun rememberBannerAd(
    activity: Any?,
    adUnitId: String = AdUnitId.BANNER_DEFAULT,
    adSize: AdSize = AdSize.FULL_BANNER,
    onLoad: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {}
): MutableState<BannerAdHandler> {
    val ad = remember(activity) { mutableStateOf(BannerAdHandler(activity)) }
    when(ad.value.state){
        AdState.DISMISSED,
        AdState.NONE -> {
            ad.value.load(
                adUnitId = adUnitId,
                adSize = adSize,
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