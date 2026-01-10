package app.lexilabs.basic.ads.composable

import androidx.annotation.RequiresPermission
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.ExperimentalBasicAdsFeature
import app.lexilabs.basic.ads.nativead.NativeAdHandler
import app.lexilabs.basic.ads.nativead.NativeAdTemplate
import app.lexilabs.basic.ads.nativead.NativeAdView as NativeAdViewWrapper

/**
 * A composable that loads and displays a native ad.
 *
 * This function handles the entire lifecycle of a native ad, from loading it using [rememberNativeAd]
 * to displaying it once it's in the [AdState.READY] state.
 *
 * @param nativeAdTemplate The composable template used to render the native ad UI.
 * @param adUnitId The ad unit ID for the native ad.
 * @param onDismissed A callback invoked when the ad is dismissed.
 * @param onShown A callback invoked when the ad is shown.
 * @param onImpression A callback invoked when an impression is recorded for the ad.
 * @param onClick A callback invoked when the ad is clicked.
 * @param onFailure A callback invoked when the ad fails to load.
 * @param onLoad A callback invoked when the ad has successfully loaded.
 */
@ExperimentalBasicAdsFeature
@DependsOnGoogleMobileAds
@RequiresPermission("android.permission.INTERNET")
@Composable
public actual fun NativeAd(
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
 * A composable that loads and displays a native ad.
 *
 * This function handles the entire lifecycle of a native ad, from loading it using [rememberNativeAd]
 * to displaying it once it's in the [AdState.READY] state.
 *
 * @param activity The current activity. This is only needed for Android implementation.
 * @param nativeAdTemplate The composable template used to render the native ad UI.
 * @param adUnitId The ad unit ID for the native ad.
 * @param onDismissed A callback invoked when the ad is dismissed.
 * @param onShown A callback invoked when the ad is shown.
 * @param onImpression A callback invoked when an impression is recorded for the ad.
 * @param onClick A callback invoked when the ad is clicked.
 * @param onFailure A callback invoked when the ad fails to load.
 * @param onLoad A callback invoked when the ad has successfully loaded.
 */
@ExperimentalBasicAdsFeature
@DependsOnGoogleMobileAds
@RequiresPermission("android.permission.INTERNET")
@Deprecated("The `activity` argument is no longer required as of v1.1.0-beta01")
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
 * A composable that displays a pre-loaded native ad from a [NativeAdHandler].
 *
 * This function should be used when you have already loaded a native ad and want to display it.
 * It renders the ad data using the provided [nativeAdTemplate].
 *
 * @param loadedAd The [NativeAdHandler] containing the pre-loaded native ad.
 * @param nativeAdTemplate The composable template used to render the native ad UI.
 */
@ExperimentalBasicAdsFeature
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
