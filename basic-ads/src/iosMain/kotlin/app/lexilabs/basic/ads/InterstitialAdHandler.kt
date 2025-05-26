package app.lexilabs.basic.ads

import androidx.annotation.MainThread
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import app.lexilabs.basic.logging.Log
import cocoapods.Google_Mobile_Ads_SDK.GADInterstitialAd
import cocoapods.Google_Mobile_Ads_SDK.GADRequest
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError

@OptIn(ExperimentalForeignApi::class)
public actual class InterstitialAdHandler actual constructor(activity: Any?) {

    private val tag = "InterstitialAd"
    private var interstitialAd: GADInterstitialAd? = null
    private var delegate: FullScreenContentDelegate? = null

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
        Log.d(tag, "load:starting")
        GADInterstitialAd.loadWithAdUnitID(
            adUnitID = adUnitId,
            request = GADRequest(),
            completionHandler = { ad: GADInterstitialAd?, error: NSError? ->
                ad?.let {
                    Log.d(tag, "load:success")
                    interstitialAd = it
                    _state.value = AdState.READY
                    onLoad()
                }
                error?.let {
                    Log.e(tag, "load:failure:$it")
                    _state.value = AdState.FAILING
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
            _state.value = AdState.FAILING
            "InterstitialAd not loaded yet. `InterstitialAd.load()` must be called first"
        }
        delegate = FullScreenContentDelegate(
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
        interstitialAd?.fullScreenContentDelegate = delegate
    }

    @MainThread
    public actual fun show() {
        _state.value = AdState.SHOWING
        Log.d(tag, "show:starting")
        require(interstitialAd != null) {
            _state.value = AdState.FAILING
            "InterstitialAd not loaded yet. `InterstitialAd.load()` must be called first"
        }
        require(delegate != null) {
            _state.value = AdState.FAILING
            "InterstitialAd listeners not set yet. `InterstitialAd.setListeners()` must be called first"
        }
        interstitialAd?.presentFromRootViewController(null)
    }

}