package app.lexilabs.basic.ads.nativead

import app.lexilabs.basic.ads.AdException
import app.lexilabs.basic.logging.Log
import cocoapods.Google_Mobile_Ads_SDK.GADAdLoader
import cocoapods.Google_Mobile_Ads_SDK.GADAdLoaderDelegateProtocol
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
public class AdLoaderDelegate(
    public val onFailure: (Exception) -> Unit = {},
    public val onLoaded: () -> Unit = {}
): NSObject(), GADAdLoaderDelegateProtocol {

    private val tag = "AdLoaderDelegate"

    override fun adLoader(
        adLoader: GADAdLoader,
        didFailToReceiveAdWithError: NSError
    ) {
        superclass
        Log.i(tag, "failure: ${didFailToReceiveAdWithError.localizedDescription}")
        onFailure(AdException(didFailToReceiveAdWithError.localizedDescription))
    }

    override fun adLoaderDidFinishLoading(adLoader: GADAdLoader) {
        superclass
        Log.i(tag, "Ad Loaded")
        onLoaded()
    }
}