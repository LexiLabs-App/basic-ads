package app.lexilabs.basic.ads

import androidx.annotation.MainThread
import androidx.compose.runtime.Composable

@DependsOnGoogleMobileAds
public expect class NativeAdHandler(activity: Any?) {

    /**
     * Determines the [AdState] of the [NativeAdHandler]
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