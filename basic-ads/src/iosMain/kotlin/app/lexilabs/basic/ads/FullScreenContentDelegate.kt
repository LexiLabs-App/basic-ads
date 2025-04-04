package app.lexilabs.basic.ads

import app.lexilabs.basic.logging.Log
import cocoapods.Google_Mobile_Ads_SDK.GADFullScreenContentDelegateProtocol
import cocoapods.Google_Mobile_Ads_SDK.GADFullScreenPresentingAdProtocol
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
public class FullScreenContentDelegate(
    private val onDismissed: () -> Unit,
    private val onShown: () -> Unit,
    private val onImpression: () -> Unit,
    private val onClick: () -> Unit,
    private val onFailure: () -> Unit,
): NSObject(), GADFullScreenContentDelegateProtocol {

    private val tag = "FullScreenContentDelegate"

    override fun ad(
        ad: GADFullScreenPresentingAdProtocol,
        didFailToPresentFullScreenContentWithError: NSError
    ) {
        super.ad(ad, didFailToPresentFullScreenContentWithError)
        Log.d(tag, "failure: ${didFailToPresentFullScreenContentWithError.localizedDescription}")
        onFailure()
    }

    override fun adDidDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
        super.adDidDismissFullScreenContent(ad)
        Log.d(tag, "ad dismissed")
        onDismissed()
    }

    override fun adDidRecordClick(ad: GADFullScreenPresentingAdProtocol) {
        super.adDidRecordClick(ad)
        Log.d(tag, "ad clicked")
        onClick()
    }

    override fun adDidRecordImpression(ad: GADFullScreenPresentingAdProtocol) {
        super.adDidRecordImpression(ad)
        Log.d(tag, "ad made an impression")
        onImpression()
    }

    override fun adWillDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
        super.adWillDismissFullScreenContent(ad)
        Log.d(tag, "ad being dismissed soon")
    }

    override fun adWillPresentFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
        super.adWillPresentFullScreenContent(ad)
        Log.d(tag, "ad being shown soon")
        onShown()
    }
}