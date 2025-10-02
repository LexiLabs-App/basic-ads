package app.lexilabs.basic.ads.nativead

import androidx.annotation.MainThread
import androidx.compose.runtime.Composable
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds

@DependsOnGoogleMobileAds
public expect class NativeAdHandler(activity: Any?) {

    /**
     * Determines the [app.lexilabs.basic.ads.AdState] of the [NativeAdHandler]
     */
    public val state: AdState

    public fun load(
        adUnitId: String = AdUnitId.NATIVE_DEFAULT,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit
    )

    public fun setListeners(
        onFailure: (Exception) -> Unit,
        onDismissed: () -> Unit,
        onShown: () -> Unit = {},
        onImpression: () -> Unit = {},
        onClick: () -> Unit = {}
    )

    /**
     * Shows the [NativeAdHandler].
     */
    @MainThread
    public fun show(
        nativeAdTemplate: @Composable (NativeAdData) -> Unit
    )
}