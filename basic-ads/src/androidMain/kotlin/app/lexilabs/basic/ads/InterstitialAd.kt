package app.lexilabs.basic.ads

import android.app.Activity
import app.lexilabs.basic.logging.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.interstitial.InterstitialAd as AndroidInterstitialAd

public actual class InterstitialAd actual constructor(
    private val activity: Any?
) {

    private val tag = "InterstitialAd"
    private var interstitialAd: AndroidInterstitialAd? = null

    public actual fun load(
        adUnitId: String,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        Log.d(tag, "loadInterstitialAd: Loading")
        require(activity != null) {
            "Activity Context must be set to non-null value in Android"
        }
        require(activity is Activity) {
            "activity variable must be of the Android `Activity` type"
        }
        AndroidInterstitialAd.load(
            activity,
            adUnitId,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    Log.d(tag, "loadInterstitialAd:failure:$adError")
                    onFailure(AdException(adError.message))
                }

                override fun onAdLoaded(ad: AndroidInterstitialAd) {
                    super.onAdLoaded(ad)
                    Log.d(tag, "loadInterstitialAd:success")
                    interstitialAd = ad
                    onLoad()
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
        Log.d(tag, "setListeners: Loading")
        require(interstitialAd != null) {
            "InterstitialAd not loaded yet. `InterstitialAd.load()` must be called first"
        }
        interstitialAd?.let {
            interstitialAd?.fullScreenContentCallback = FullscreenContentDelegate(
                onClick = onClick,
                onDismissed = onDismissed,
                onFailure = onFailure,
                onImpression = onImpression,
                onShown = onShown
            )
        } ?: Log.d(tag, "The interstitial ad wasn't ready yet.")
    }

    public actual fun show() {
        Log.d(tag, "show: Loading")
        require(activity != null) {
            "Activity Context must be set to non-null value in Android"
        }
        require(activity is Activity) {
            "activity variable must be of the Android `Activity` type"
        }
        require(interstitialAd != null) {
            "InterstitialAd not loaded yet. `InterstitialAd.load()` must be called first"
        }
        interstitialAd?.show(activity)
    }

}