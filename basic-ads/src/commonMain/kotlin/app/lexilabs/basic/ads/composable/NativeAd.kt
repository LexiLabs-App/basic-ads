package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.nativead.NativeAdData
import app.lexilabs.basic.ads.nativead.NativeAdHandler

@DependsOnGoogleMobileAds
@Composable
public fun NativeAd(
    activity: Any?,
    nativeAdTemplate: @Composable (NativeAdData) -> Unit, // TODO: Create a default nativeAdTemplate
    adUnitId: String = AdUnitId.NATIVE_DEFAULT,
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
    onLoad: () -> Unit = {}
) {
    val ad by rememberNativeAd(
        activity = activity,
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
    ad.show(nativeAdTemplate)
}

@DependsOnGoogleMobileAds
@Composable
public fun NativeAd(
    loadedAd: NativeAdHandler,
    nativeAdTemplate: @Composable (NativeAdData) -> Unit, // TODO: Create a default nativeAdTemplate
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
    onLoad: () -> Unit = {}
) {
    loadedAd.setListeners(
        onFailure = onFailure,
        onDismissed = onDismissed,
        onShown = onShown,
        onImpression = onImpression,
        onClick = onClick
    )
    loadedAd.show(nativeAdTemplate)
}
