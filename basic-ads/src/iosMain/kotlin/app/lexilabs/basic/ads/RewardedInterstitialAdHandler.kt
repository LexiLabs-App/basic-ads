package app.lexilabs.basic.ads

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
    public actual var state: AdState = AdState.NONE

    public actual fun load(
        adUnitId: String,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        state = AdState.LOADING
        Log.d(tag, "load:starting")
        GADRewardedInterstitialAd.loadWithAdUnitID(
            adUnitID = adUnitId,
            request = GADRequest(),
            completionHandler = { ad: GADRewardedInterstitialAd?, error: NSError? ->
                ad?.let {
                    Log.d(tag, "load:success")
                    rewardedInterstitialAd = it
                    state = AdState.READY
                    onLoad()
                }
                error?.let {
                    Log.e(tag, "load:failure:$it")
                    state = AdState.FAILING
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
            state = AdState.FAILING
            "RewardedAd not loaded yet. `RewardedAd.load()` must be called first"
        }
        delegate = FullScreenContentDelegate(
            onClick = onClick,
            onDismissed = {
                state = AdState.DISMISSED
                onDismissed()
            },
            onFailure = {
                state = AdState.FAILING
                onFailure(it)
            },
            onImpression = onImpression,
            onShown = {
                state = AdState.SHOWN
                onShown()
            }
        )
        rewardedInterstitialAd?.fullScreenContentDelegate = delegate
    }

    public actual fun show(onRewardEarned: () -> Unit) {
        state = AdState.SHOWING
        Log.d(tag, "show:starting")
        require(rewardedInterstitialAd != null) {
            state = AdState.FAILING
            "RewardedAd not loaded yet. `RewardedAd.load()` must be called first"
        }
        require(delegate != null) {
            state = AdState.FAILING
            "RewardedAd listeners not set yet. `RewardedAd.setListeners()` must be called first"
        }
        rewardedInterstitialAd?.presentFromRootViewController(
            viewController = null,
            userDidEarnRewardHandler = { onRewardEarned() }
        )
    }
}