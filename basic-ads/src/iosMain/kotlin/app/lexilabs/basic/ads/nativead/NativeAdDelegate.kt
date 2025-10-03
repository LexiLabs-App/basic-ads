package app.lexilabs.basic.ads.nativead

import app.lexilabs.basic.logging.Log
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAd
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAdDelegateProtocol
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
public class NativeAdDelegate(
    private val onImpression: () -> Unit,
    private val onClick: () -> Unit,
    private val onShown: () -> Unit,
    private val onDismissed: () -> Unit
): NSObject(), GADNativeAdDelegateProtocol {

    private val tag = "NativeAdDelegate"

    override fun nativeAdDidRecordImpression(nativeAd: GADNativeAd) {
        superclass
        Log.i(tag, "Ad made an impression")
        onImpression()
    }

    override fun nativeAdDidRecordClick(nativeAd: GADNativeAd) {
        superclass
        Log.i(tag, "User clicked ad")
        onClick()
    }

    override fun nativeAdWillPresentScreen(nativeAd: GADNativeAd) {
        superclass
        Log.i(tag, "Ad is being shown")
        onShown()
    }

    override fun nativeAdWillDismissScreen(nativeAd: GADNativeAd) {
        superclass
        Log.i(tag, "Ad is being dismissed")
    }

    override fun nativeAdDidDismissScreen(nativeAd: GADNativeAd) {
        superclass
        Log.i(tag, "Ad was dismissed")
        onDismissed()
    }
}