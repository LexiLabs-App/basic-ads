package app.lexilabs.basic.ads

import android.app.Activity
import androidx.annotation.MainThread
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import app.lexilabs.basic.logging.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.interstitial.InterstitialAd as AndroidInterstitialAd

public actual class InterstitialAdHandler actual constructor(
    private val activity: Any?
) {

    private val tag = "InterstitialAd"
    private var interstitialAd: AndroidInterstitialAd? = null

    private val _state: MutableState<AdState> = mutableStateOf(AdState.NONE)

    /**
     * Determines the [AdState] of the [InterstitialAdHandler]
     */
    public actual val state: AdState by _state

    public actual fun load(
        adUnitId: String,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        _state.value = AdState.LOADING
        Log.d(tag, "loadInterstitialAd: Loading")
        require(activity != null) {
            _state.value = AdState.FAILING
            "Activity Context must be set to non-null value in Android"
        }
        require(activity is Activity) {
            _state.value = AdState.FAILING
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
                    _state.value = AdState.FAILING
                    onFailure(AdException(adError.message))
                }

                override fun onAdLoaded(ad: AndroidInterstitialAd) {
                    super.onAdLoaded(ad)
                    Log.d(tag, "loadInterstitialAd:success")
                    interstitialAd = ad
                    _state.value = AdState.READY
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
            _state.value = AdState.FAILING
            "InterstitialAd not loaded yet. `InterstitialAd.load()` must be called first"
        }
        interstitialAd?.let {
            interstitialAd?.fullScreenContentCallback = FullscreenContentDelegate(
                onClick = onClick,
                onDismissed = {
                    _state.value = AdState.DISMISSED
                    onDismissed()
                },
                onFailure = {
                    _state.value = AdState.FAILING
                    onFailure(it)
                },
                onImpression = onImpression,
                onShown = {
                    _state.value = AdState.SHOWN
                    onShown()
                }
            )
        } ?: Log.d(tag, "The interstitial ad wasn't ready yet.")
    }

    @MainThread
    public actual fun show() {
        _state.value = AdState.SHOWING
        Log.d(tag, "show: Loading")
        require(activity != null) {
            _state.value = AdState.FAILING
            "Activity Context must be set to non-null value in Android"
        }
        require(activity is Activity) {
            _state.value = AdState.FAILING
            "activity variable must be of the Android `Activity` type"
        }
        require(interstitialAd != null) {
            _state.value = AdState.FAILING
            "InterstitialAd not loaded yet. `InterstitialAd.load()` must be called first"
        }
        interstitialAd?.show(activity)
    }

}