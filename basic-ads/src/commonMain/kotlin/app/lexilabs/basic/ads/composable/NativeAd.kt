package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.nativead.NativeAdHandler
import app.lexilabs.basic.ads.nativead.NativeAdTemplate

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
public expect fun NativeAd(
    activity: Any?,
    nativeAdTemplate: NativeAdTemplate,
    adUnitId: String = AdUnitId.NATIVE_DEFAULT,
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
    onLoad: () -> Unit = {}
)

/**
 * A composable that displays a native ad.
 * @param loadedAd the pre-loaded native ad
 * @param nativeAdTemplate the composable that will be used to display the native ad
 */
@DependsOnGoogleMobileAds
@Composable
public expect fun NativeAd(
    loadedAd: NativeAdHandler,
    nativeAdTemplate: NativeAdTemplate,
)
