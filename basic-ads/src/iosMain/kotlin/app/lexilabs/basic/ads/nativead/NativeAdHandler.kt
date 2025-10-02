package app.lexilabs.basic.ads.nativead

import androidx.annotation.MainThread
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.getRootViewController
import cocoapods.Google_Mobile_Ads_SDK.GADAdLoader
import cocoapods.Google_Mobile_Ads_SDK.GADAdLoaderAdTypeNative
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAd
import cocoapods.Google_Mobile_Ads_SDK.GADRequest
import kotlinx.cinterop.ExperimentalForeignApi

@DependsOnGoogleMobileAds
public actual class NativeAdHandler actual constructor(activity: Any?) {

    private val tag = "NativeAd"
    @OptIn(ExperimentalForeignApi::class)
    private var nativeAd: GADNativeAd? = null
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
        val adLoader = GADAdLoader(
            adUnitID = adUnitId,
            rootViewController = getRootViewController(),
            adTypes = listOf(GADAdLoaderAdTypeNative),
            options = null
        )
        adLoader.delegate = AdLoaderDelegate(
            onFailure = onFailure
        )
        adLoader.loadRequest(GADRequest())
        TODO("Load an ad, use the NativeAdDelegate to set the listeners")
    }

    /**
     * Shows the [app.lexilabs.basic.ads.nativead.NativeAdHandler].
     */
    @OptIn(ExperimentalForeignApi::class)
    @MainThread
    public actual fun render(): NativeAdData {
        require(nativeAd != null) {
            "NativeAd is null"
        }
        return nativeAd!!.toCommon()
    }

    @OptIn(ExperimentalForeignApi::class)
    public actual fun destroy() {
        TODO()
    }
}