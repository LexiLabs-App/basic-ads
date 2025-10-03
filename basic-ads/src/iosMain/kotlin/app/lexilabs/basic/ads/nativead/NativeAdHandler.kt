package app.lexilabs.basic.ads.nativead

import androidx.annotation.MainThread
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import kotlinx.cinterop.ExperimentalForeignApi

@DependsOnGoogleMobileAds
public actual class NativeAdHandler actual constructor(activity: Any?) {

    @OptIn(ExperimentalForeignApi::class)
    private var adLoader: NativeAdLoader? = null
    private val _state: MutableState<AdState> = mutableStateOf(AdState.NONE)

    /**
     * Determines the [AdState] of the [app.lexilabs.basic.ads.nativead.NativeAdHandler]
     */
    public actual val state: AdState by _state

    @OptIn(ExperimentalForeignApi::class)
    public actual fun load(
        adUnitId: String,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit,
        onDismissed: () -> Unit,
        onShown: () -> Unit,
        onImpression: () -> Unit,
        onClick: () -> Unit
    ) {
        adLoader = NativeAdLoader(
            adUnitId = adUnitId,
            onLoad =  {
                _state.value = AdState.READY
                onLoad()
            },
            onFailure = {
                _state.value = AdState.FAILING
                onFailure(it)
            },
            onDismissed = {
                _state.value = AdState.DISMISSED
                onDismissed()
            },
            onShown = {
                _state.value = AdState.SHOWN
                onShown()
            },
            onImpression = {
                _state.value = AdState.SHOWING
                onImpression()
            },
            onClick = {
                onClick()
            }
        )
    }

    /**
     * Shows the [app.lexilabs.basic.ads.nativead.NativeAdHandler].
     */
    @OptIn(ExperimentalForeignApi::class)
    @MainThread
    public actual fun render(): NativeAdData {
        require(adLoader?.nativeAd != null) {
            "NativeAd is null"
        }
        return adLoader!!.nativeAd!!.toCommon()
    }

    @OptIn(ExperimentalForeignApi::class)
    public actual fun destroy() {
        adLoader?.nativeAd = null
    }
}