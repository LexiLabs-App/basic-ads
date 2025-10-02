package app.lexilabs.basic.ads.nativead

import androidx.annotation.MainThread
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import com.google.android.gms.ads.nativead.NativeAd

@DependsOnGoogleMobileAds
public actual class NativeAdHandler actual constructor(activity: Any?) {

    private val tag = "NativeAd"
    private var nativeAd: NativeAd? = null
    private val _state: MutableState<AdState> = mutableStateOf(AdState.NONE)

    /**
     * Determines the [AdState] of the [app.lexilabs.basic.ads.nativead.NativeAdHandler]
     */
    public actual val state: AdState by _state

    public actual fun load(
        adUnitId: String,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        TODO()
    }

    public actual fun setListeners(
        onFailure: (Exception) -> Unit,
        onDismissed: () -> Unit,
        onShown: () -> Unit,
        onImpression: () -> Unit,
        onClick: () -> Unit
    ) {
        TODO()
    }

    /**
     * Shows the [app.lexilabs.basic.ads.nativead.NativeAdHandler].
     */
    @MainThread
    public actual fun show(
        nativeAdTemplate: @Composable (NativeAdData) -> Unit
    ) {
        TODO()
    }
}