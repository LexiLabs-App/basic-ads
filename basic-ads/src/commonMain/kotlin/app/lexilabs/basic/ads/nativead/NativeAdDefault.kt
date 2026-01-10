package app.lexilabs.basic.ads.nativead

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.lexilabs.basic.ads.ExperimentalBasicAdsFeature

@ExperimentalBasicAdsFeature
public expect class NativeAdDefault public constructor(
    nativeAdData: NativeAdData? = null
): NativeAdTemplate {

    override val nativeAdData: NativeAdData?

    @Composable
    override fun Show(modifier: Modifier)

}