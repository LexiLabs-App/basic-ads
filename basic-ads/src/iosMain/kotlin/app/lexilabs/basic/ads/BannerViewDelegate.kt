package app.lexilabs.basic.ads

import app.lexilabs.basic.logging.Log
import cocoapods.Google_Mobile_Ads_SDK.GADBannerView
import cocoapods.Google_Mobile_Ads_SDK.GADBannerViewDelegateProtocol
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
public class BannerViewDelegate(
    private val onLoad: () -> Unit,
    private val onWillDismiss: () -> Unit,
    private val onDismissed: () -> Unit,
    private val onShown: () -> Unit,
    private val onImpression: () -> Unit,
    private val onClick: () -> Unit,
    private val onFailure: (Exception) -> Unit
): NSObject(), GADBannerViewDelegateProtocol {

    private val tag = "BannerViewDelegate"

    override fun bannerViewDidReceiveAd(bannerView: GADBannerView) {
        superclass
        Log.d(tag, "BannerAd received")
        onLoad()
    }

    override fun bannerView(bannerView: GADBannerView, didFailToReceiveAdWithError: NSError) {
        superclass
        Log.d(tag, "failure: ${didFailToReceiveAdWithError.localizedDescription}")
        onFailure(AdException(didFailToReceiveAdWithError.localizedDescription))
    }

    override fun bannerViewDidRecordClick(bannerView: GADBannerView) {
        superclass
        Log.d(tag, "BannerAd clicked")
        onClick()
    }

    override fun bannerViewDidRecordImpression(bannerView: GADBannerView) {
        superclass
        Log.d(tag, "BannerAd left impression")
        onImpression()
    }

    override fun bannerViewWillPresentScreen(bannerView: GADBannerView) {
        superclass
        Log.d(tag, "BannerAd is showing")
        onShown()
    }

    override fun bannerViewWillDismissScreen(bannerView: GADBannerView) {
        superclass
        Log.d(tag, "BannerAd is dismissing soon")
        onWillDismiss()
    }

    override fun bannerViewDidDismissScreen(bannerView: GADBannerView) {
        superclass
        Log.d(tag, "BannerAd dismissed")
        onDismissed()
    }
}