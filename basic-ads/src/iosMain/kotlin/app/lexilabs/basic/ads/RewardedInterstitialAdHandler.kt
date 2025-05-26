package app.lexilabs.basic.ads

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import app.lexilabs.basic.logging.Log
import cocoapods.Google_Mobile_Ads_SDK.GADRequest
import cocoapods.Google_Mobile_Ads_SDK.GADRewardedInterstitialAd
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError

@OptIn(ExperimentalForeignApi::class)
public actual class RewardedInterstitialAdHandler actual constructor(activity: Any?) {

    private val tag = "RewardedInterstitialAd"
    private var rewardedInterstitialAd: GADRewardedInterstitialAd? = null
    private var delegate: FullScreenContentDelegate? = null
    private val _state: MutableState<AdState> = mutableStateOf(AdState.NONE)

    public actual val state: AdState by _state

    public actual fun load(
        adUnitId: String,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        _state.value = AdState.LOADING
        Log.d(tag, "load:starting")
        GADRewardedInterstitialAd.loadWithAdUnitID(
            adUnitID = adUnitId,
            request = GADRequest(),
            completionHandler = { ad: GADRewardedInterstitialAd?, error: NSError? ->
                ad?.let {
                    Log.d(tag, "load:success")
                    rewardedInterstitialAd = it
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
        require(rewardedInterstitialAd != null) {
            _state.value = AdState.FAILING
            "RewardedAd not loaded yet. `RewardedAd.load()` must be called first"
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
        rewardedInterstitialAd?.fullScreenContentDelegate = delegate
    }

    public actual fun show(onRewardEarned: () -> Unit) {
        _state.value = AdState.SHOWING
        Log.d(tag, "show:starting")
        require(rewardedInterstitialAd != null) {
            _state.value = AdState.FAILING
            "RewardedAd not loaded yet. `RewardedAd.load()` must be called first"
        }
        require(delegate != null) {
            _state.value = AdState.FAILING
            "RewardedAd listeners not set yet. `RewardedAd.setListeners()` must be called first"
        }
        rewardedInterstitialAd?.presentFromRootViewController(
            viewController = null,
            userDidEarnRewardHandler = { onRewardEarned() }
        )
    }
}