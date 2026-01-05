package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
public expect fun rememberNativeAd(
    adUnitId: String = AdUnitId.NATIVE_DEFAULT,
    onLoad: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
): MutableState<NativeAdHandler>

/**
 * Remembers a [NativeAdHandler] across compositions.
 * @param activity the current Activity (only needed for Android Impl)
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
@Deprecated("The `activity` argument is no longer required as of v1.1.0-beta01")
@Composable
public expect fun rememberNativeAd(
    activity: Any?,
    adUnitId: String = AdUnitId.NATIVE_DEFAULT,
    onLoad: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
): MutableState<NativeAdHandler>