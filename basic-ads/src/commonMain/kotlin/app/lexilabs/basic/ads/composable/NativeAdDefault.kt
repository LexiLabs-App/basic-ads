package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import app.lexilabs.basic.ads.nativead.NativeAdData

/**
 * A default implementation of a native ad.
 * @param activity the current Activity (only needed for Android Impl)
 * @param nativeAd the native ad to display
 */
@Composable
public expect fun NativeAdDefault(activity: Any?, nativeAd: NativeAdData)