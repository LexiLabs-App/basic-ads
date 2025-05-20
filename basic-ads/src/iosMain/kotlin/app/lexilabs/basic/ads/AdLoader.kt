package app.lexilabs.basic.ads

import app.lexilabs.basic.logging.Log
import cocoapods.Google_Mobile_Ads_SDK.GADInterstitialAd
import cocoapods.Google_Mobile_Ads_SDK.GADRequest
import cocoapods.Google_Mobile_Ads_SDK.GADRewardedAd
import cocoapods.Google_Mobile_Ads_SDK.GADRewardedInterstitialAd
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError

@OptIn(ExperimentalForeignApi::class)
public actual typealias AdRequest = GADRequest

@OptIn(ExperimentalForeignApi::class)
public actual class AdLoader {

    private val tag = "AdLoader"

    private var interstitialAd: GADInterstitialAd? = null
    private var interstitialAdId: String = ""

    private var rewardedInterstitialAd: GADRewardedInterstitialAd? = null
    private var rewardedInterstitialAdId: String = ""

    private var rewardedAd: GADRewardedAd? = null
    private var rewardedAdId: String = ""

    public actual fun requestAd(): AdRequest = GADRequest()

    public actual fun loadInterstitialAd(
        activity: Any?, /* This variable is unused, but necessary for Android */
        adUnitId: String,
        onLoaded: () -> Unit,
        onFailedToLoad: (Long) -> Unit,
    ) {
        interstitialAdId = adUnitId
        GADInterstitialAd.loadWithAdUnitID(
            adUnitID = interstitialAdId,
            request = requestAd(),
            completionHandler = { ad: GADInterstitialAd?, error: NSError? ->
                ad?.let {
                    Log.d(tag, "loadInterstitialAd:success")
                    interstitialAd = it
                    onLoaded()
                }
                error?.let {
                    Log.e(tag, "loadInterstitialAd:failure:$it")
                    onFailedToLoad(it.code)
                }
            }
        )
    }

    public actual fun showInterstitialAd(
        activity: Any?, /* This variable is unused, but necessary for Android */
        onDismissed: () -> Unit,
        onShown: () -> Unit,
        onImpression: () -> Unit,
        onClick: () -> Unit,
        onFailure: () -> Unit,
    ){
        interstitialAd?.let { ad ->
            ad.fullScreenContentDelegate = FullScreenContentDelegate(
                onClick = onClick,
                onImpression = onImpression,
                onDismissed = onDismissed,
                onFailure = { _: Exception -> onFailure() },
                onShown = onShown
            )
            ad.presentFromRootViewController(null)
        } ?: Log.d(tag, "The interstitial ad wasn't ready yet.")
    }

    public actual fun loadRewardedInterstitialAd(
        activity: Any?, /* This variable is unused, but necessary for Android */
        adUnitId: String,
        onLoaded: () -> Unit,
        onFailedToLoad: (Long) -> Unit,
    ) {
        rewardedInterstitialAdId = adUnitId
        GADRewardedInterstitialAd.loadWithAdUnitID(
            adUnitID = rewardedInterstitialAdId,
            request = requestAd(),
            completionHandler = { ad: GADRewardedInterstitialAd?, error: NSError? ->
                ad?.let {
                    Log.d(tag, "loadRewardedInterstitialAd:success")
                    rewardedInterstitialAd = it
                    onLoaded()
                }
                error?.let {
                    Log.e(tag, "loadRewardedInterstitialAd:failure:$it")
                    onFailedToLoad(it.code)
                }
            }
        )
    }

    public actual fun showRewardedInterstitialAd(
        activity: Any?, /* This variable is unused, but necessary for Android */
        onRewardEarned: () -> Unit,
        onDismissed: () -> Unit,
        onShown: () -> Unit,
        onImpression: () -> Unit,
        onClick: () -> Unit,
        onFailure: () -> Unit,
    ) {
        rewardedInterstitialAd?.let { ad ->
            ad.fullScreenContentDelegate = FullScreenContentDelegate(
                onClick = onClick,
                onDismissed = onDismissed,
                onFailure = { _: Exception -> onFailure() },
                onImpression = onImpression,
                onShown = onShown
            )
            ad.presentFromRootViewController(
                viewController = null,
                userDidEarnRewardHandler = { onRewardEarned() }
            )
        } ?: Log.d(tag, "The rewarded interstitial ad wasn't ready yet.")
    }

    public actual fun loadRewardedAd(
        activity: Any?, /* This variable is unused, but necessary for Android */
        adUnitId: String,
        onLoaded: () -> Unit,
        onFailedToLoad: (Long) -> Unit,
    ) {
        rewardedAdId = adUnitId
        GADRewardedAd.loadWithAdUnitID(
            adUnitID = rewardedAdId,
            request = requestAd(),
            completionHandler = { ad: GADRewardedAd?, error: NSError? ->
                ad?.let {
                    Log.d(tag, "loadRewardedAd:success")
                    rewardedAd = it
                    onLoaded()
                }
                error?.let {
                    Log.e(tag, "loadRewardedAd:failure:$it")
                    onFailedToLoad(it.code)
                }
            }
        )
    }

    public actual fun showRewardedAd(
        activity: Any?, /* This variable is unused, but necessary for Android */
        onRewardEarned: () -> Unit,
        onDismissed: () -> Unit,
        onShown: () -> Unit,
        onImpression: () -> Unit,
        onClick: () -> Unit,
        onFailure: () -> Unit,
    ) {
        rewardedAd?.let { ad ->
            ad.fullScreenContentDelegate = FullScreenContentDelegate(
                onClick = onClick,
                onDismissed = onDismissed,
                onFailure = { _: Exception -> onFailure() },
                onImpression = onImpression,
                onShown = onShown
            )
            ad.presentFromRootViewController(
                rootViewController = null,
                userDidEarnRewardHandler = { onRewardEarned() }
            )
        } ?: Log.d(tag, "The rewarded ad wasn't ready yet.")
    }

}