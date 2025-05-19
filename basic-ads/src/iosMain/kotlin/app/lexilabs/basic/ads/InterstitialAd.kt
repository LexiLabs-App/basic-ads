package app.lexilabs.basic.ads

import androidx.annotation.MainThread
import app.lexilabs.basic.logging.Log
import cocoapods.Google_Mobile_Ads_SDK.GADInterstitialAd
import cocoapods.Google_Mobile_Ads_SDK.GADRequest
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError

@OptIn(ExperimentalForeignApi::class)
public actual class InterstitialAd actual constructor(activity: Any?) {

    private val tag = "InterstitialAd"
    private var interstitialAd: GADInterstitialAd? = null
    private var delegate: FullScreenContentDelegate? = null
    
    public actual fun load(
        adUnitId: String,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        Log.d(tag, "load:starting")
        GADInterstitialAd.loadWithAdUnitID(
            adUnitID = adUnitId,
            request = GADRequest(),
            completionHandler = { ad: GADInterstitialAd?, error: NSError? ->
                ad?.let {
                    Log.d(tag, "load:success")
                    interstitialAd = it
                    onLoad()
                }
                error?.let {
                    Log.e(tag, "load:failure:$it")
                    onFailure(AdException())
                }
            }
        )
    }

    public actual fun setListeners(
        onFailure: (Exception) -> Unit,
        onDismissed: () -> Unit,
        onShown: () -> Unit,
        onImpression: () -> Unit,
        onClick: () -> Unit
    ) {
        Log.d(tag, "setListeners:starting")
        require(interstitialAd != null) {
            "InterstitialAd not loaded yet. `InterstitialAd.load()` must be called first"
        }
        delegate = FullScreenContentDelegate(
            onClick = onClick,
            onDismissed = onDismissed,
            onFailure = onFailure,
            onImpression = onImpression,
            onShown = onShown
        )
        interstitialAd?.fullScreenContentDelegate = delegate
    }

    @MainThread
    public actual fun show() {
        Log.d(tag, "show:starting")
        require(interstitialAd != null) {
            "InterstitialAd not loaded yet. `InterstitialAd.load()` must be called first"
        }
        require(delegate != null) {
            "InterstitialAd listeners not set yet. `InterstitialAd.setListeners()` must be called first"
        }
        interstitialAd?.presentFromRootViewController(null)
    }

}