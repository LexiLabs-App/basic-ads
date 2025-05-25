package app.lexilabs.basic.ads

import app.lexilabs.basic.logging.Log
import cocoapods.Google_Mobile_Ads_SDK.GADFullScreenContentDelegateProtocol
import cocoapods.Google_Mobile_Ads_SDK.GADFullScreenPresentingAdProtocol
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
public class FullScreenContentDelegate(
    private val onDismissed: () -> Unit,
    private val onShown: () -> Unit,
    private val onImpression: () -> Unit,
    private val onClick: () -> Unit,
    private val onFailure: (Exception) -> Unit
): NSObject(), GADFullScreenContentDelegateProtocol {

    private val tag = "FullScreenContentDelegate"

    override fun ad(
        ad: GADFullScreenPresentingAdProtocol,
        didFailToPresentFullScreenContentWithError: NSError
    ) {
        superclass
        Log.d(tag, "failure: ${didFailToPresentFullScreenContentWithError.localizedDescription}")
        onFailure(AdException(didFailToPresentFullScreenContentWithError.localizedDescription))
    }

    override fun adDidDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
        superclass
        Log.d(tag, "ad dismissed")
        onDismissed()
    }

    override fun adDidRecordClick(ad: GADFullScreenPresentingAdProtocol) {
        superclass
        Log.d(tag, "ad clicked")
        onClick()
    }

    override fun adDidRecordImpression(ad: GADFullScreenPresentingAdProtocol) {
        superclass
        Log.d(tag, "ad made an impression")
        onImpression()
    }

    override fun adWillDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
        superclass
        Log.d(tag, "ad being dismissed soon")
        onDismissed()
    }

    override fun adWillPresentFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
        superclass
        Log.d(tag, "ad being shown soon")
        onShown()
    }
}