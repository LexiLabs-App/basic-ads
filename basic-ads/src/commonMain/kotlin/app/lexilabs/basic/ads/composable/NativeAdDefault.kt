package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import app.lexilabs.basic.ads.nativead.NativeAdData

@Composable
public expect fun NativeAdDefault(activity: Any?, nativeAd: NativeAdData)