package app.lexilabs.basic.ads

import app.lexilabs.basic.logging.Log
import cocoapods.Google_Mobile_Ads_SDK.GADFullScreenContentDelegateProtocol
import cocoapods.Google_Mobile_Ads_SDK.GADFullScreenPresentingAdProtocol
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSError
import platform.UIKit.UIViewController

@OptIn(ExperimentalForeignApi::class)
public class FullScreenContentDelegate(
    private val onDismissed: () -> Unit,
    private val onShown: () -> Unit,
    private val onImpression: () -> Unit,
    private val onClick: () -> Unit,
    private val onFailure: () -> Unit,
): UIViewController("FullScreenContentDelegate", NSBundle()), GADFullScreenContentDelegateProtocol {

    private val tag = "FullScreenContentDelegate"

    override fun ad(
        ad: GADFullScreenPresentingAdProtocol,
        didFailToPresentFullScreenContentWithError: NSError
    ) {
        Log.d(tag, "failure: ${didFailToPresentFullScreenContentWithError.localizedDescription}")
        onFailure()
    }

    override fun adDidDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
        Log.d(tag, "ad dismissed")
        onDismissed()
    }

    override fun adDidRecordClick(ad: GADFullScreenPresentingAdProtocol) {
        Log.d(tag, "ad clicked")
        onClick()
    }

    override fun adDidRecordImpression(ad: GADFullScreenPresentingAdProtocol) {
        Log.d(tag, "ad made an impression")
        onImpression()
    }

    override fun adWillDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
        Log.d(tag, "ad being dismissed soon")
        onDismissed()
    }

    override fun adWillPresentFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
        Log.d(tag, "ad being shown soon")
        onShown()
    }
}