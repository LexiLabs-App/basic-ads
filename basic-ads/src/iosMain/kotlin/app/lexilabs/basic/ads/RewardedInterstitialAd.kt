package app.lexilabs.basic.ads

import app.lexilabs.basic.logging.Log
import cocoapods.Google_Mobile_Ads_SDK.GADRequest
import cocoapods.Google_Mobile_Ads_SDK.GADRewardedInterstitialAd
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError

@OptIn(ExperimentalForeignApi::class)
public actual class RewardedInterstitialAd actual constructor(activity: Any?) {

    private val tag = "RewardedInterstitialAd"
    private var rewardedInterstitialAd: GADRewardedInterstitialAd? = null
    private var delegate: FullScreenContentDelegate? = null

    public actual fun load(
        adUnitId: String,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        Log.d(tag, "load:starting")
        GADRewardedInterstitialAd.loadWithAdUnitID(
            adUnitID = adUnitId,
            request = GADRequest(),
            completionHandler = { ad: GADRewardedInterstitialAd?, error: NSError? ->
                ad?.let {
                    Log.d(tag, "load:success")
                    rewardedInterstitialAd = it
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
        require(rewardedInterstitialAd != null) {
            "RewardedAd not loaded yet. `RewardedAd.load()` must be called first"
        }
        delegate = FullScreenContentDelegate(
            onClick = onClick,
            onDismissed = onDismissed,
            onFailure = onFailure,
            onImpression = onImpression,
            onShown = onShown
        )
        rewardedInterstitialAd?.fullScreenContentDelegate = delegate
    }

    public actual fun show(onRewardEarned: () -> Unit) {
        Log.d(tag, "show:starting")
        require(rewardedInterstitialAd != null) {
            "RewardedAd not loaded yet. `RewardedAd.load()` must be called first"
        }
        require(delegate != null) {
            "RewardedAd listeners not set yet. `RewardedAd.setListeners()` must be called first"
        }
        rewardedInterstitialAd?.presentFromRootViewController(
            viewController = null,
            userDidEarnRewardHandler = { onRewardEarned() }
        )
    }
}