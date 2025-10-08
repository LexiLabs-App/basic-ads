package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.nativead.NativeAdHandler
import app.lexilabs.basic.ads.nativead.NativeAdTemplate
import app.lexilabs.basic.ads.nativead.NativeAdView as NativeAdViewWrapper

/**
 * A composable that displays a native ad.
 * @param activity the current Activity (only needed for Android Impl)
 * @param nativeAdTemplate the composable that will be used to display the native ad
 * @param adUnitId the ad unit ID for the native ad
 * @param onDismissed a callback that will be invoked when the ad is dismissed
 * @param onShown a callback that will be invoked when the ad is shown
 * @param onImpression a callback that will be invoked when an impression is recorded for the ad
 * @param onClick a callback that will be invoked when the ad is clicked
 * @param onFailure a callback that will be invoked when the ad fails to load
 * @param onLoad a callback that will be invoked when the ad has loaded
 */
@DependsOnGoogleMobileAds
@Composable
public actual fun NativeAd(
    activity: Any?,
    nativeAdTemplate: NativeAdTemplate,
    adUnitId: String,
    onDismissed: () -> Unit,
    onShown: () -> Unit,
    onImpression: () -> Unit,
    onClick: () -> Unit,
    onFailure: (Exception) -> Unit,
    onLoad: () -> Unit
) {
    val ad by rememberNativeAd(
        activity = activity,
        adUnitId = adUnitId,
        onLoad = onLoad,
        onFailure = onFailure,
        onDismissed = onDismissed,
        onShown = onShown,
        onImpression = onImpression,
        onClick = onClick
    )

    if (ad.state == AdState.READY) {
        NativeAd(loadedAd = ad, nativeAdTemplate = nativeAdTemplate)
    }
}

/**
 * A composable that displays a native ad.
 * @param loadedAd the pre-loaded native ad
 * @param nativeAdTemplate the composable that will be used to display the native ad
 */
@DependsOnGoogleMobileAds
@Composable
public actual fun NativeAd(
    loadedAd: NativeAdHandler,
    nativeAdTemplate: NativeAdTemplate
) {
    if (loadedAd.state != AdState.READY) { return }

    val adData = loadedAd.render()

    NativeAdViewWrapper(nativeAd = adData){
        nativeAdTemplate.copy(it).Show()
    }
}
