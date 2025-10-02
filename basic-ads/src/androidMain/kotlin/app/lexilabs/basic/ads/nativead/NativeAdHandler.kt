package app.lexilabs.basic.ads.nativead

import android.app.Activity
import androidx.annotation.MainThread
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import app.lexilabs.basic.ads.AdException
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.logging.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@DependsOnGoogleMobileAds
public actual class NativeAdHandler actual constructor(private val activity: Any?) {

    private val tag = "NativeAd"
    public var nativeAd: NativeAd? = null
    private val _state: MutableState<AdState> = mutableStateOf(AdState.NONE)

    /**
     * Determines the [AdState] of the [app.lexilabs.basic.ads.nativead.NativeAdHandler]
     */
    public actual val state: AdState by _state

    @RequiresPermission(android.Manifest.permission.INTERNET)
    public actual fun load(
        adUnitId: String,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit,
        onDismissed: () -> Unit,
        onShown: () -> Unit,
        onImpression: () -> Unit,
        onClick: () -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            _state.value = AdState.LOADING
            val adLoader = AdLoader.Builder(activity as Activity, adUnitId)
                .forNativeAd {
                    nativeAd = it
                    _state.value = AdState.READY
                    onLoad()
                }
                .withAdListener(
                    object : AdListener() {
                        override fun onAdFailedToLoad(adError: LoadAdError) {
                            // The native ad load failed. Check the adError message for failure reasons.
                            _state.value = AdState.FAILING
                            Log.e(tag, "failure: ${adError.message}")
                            onFailure(AdException(adError.message))
                        }

                        override fun onAdClicked() {
                            super.onAdClicked()
                            Log.i(tag, "Ad Clicked")
                            onClick()
                        }

                        override fun onAdClosed() {
                            super.onAdClosed()
                            Log.i(tag, "Ad Dismissed")
                            onDismissed()
                        }

                        override fun onAdImpression() {
                            super.onAdImpression()
                            Log.i(tag, "Ad made an impression")
                            onImpression()
                        }

                        override fun onAdOpened() {
                            super.onAdOpened()
                            Log.i(tag, "Ad Opened")
                            onShown()
                        }
                    }
                )
                // Use the NativeAdOptions.Builder class to specify individual options settings.
                .withNativeAdOptions(NativeAdOptions.Builder().build())
                .build()
            adLoader.loadAd(AdRequest.Builder().build())
        }
    }

    /**
     * Shows the [app.lexilabs.basic.ads.nativead.NativeAdHandler].
     */
    @MainThread
    public actual fun render(): NativeAdData {
        require(nativeAd != null){
            "NativeAd has not loaded"
        }
        return nativeAd!!.toCommon()
    }

    public actual fun destroy() {
        nativeAd?.destroy()
    }
}