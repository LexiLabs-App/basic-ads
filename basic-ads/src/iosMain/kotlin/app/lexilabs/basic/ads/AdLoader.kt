package app.lexilabs.basic.ads

import app.lexilabs.basic.logging.Log
import cocoapods.Google_Mobile_Ads_SDK.GADFullScreenContentDelegateProtocol
import cocoapods.Google_Mobile_Ads_SDK.GADFullScreenPresentingAdProtocol
import cocoapods.Google_Mobile_Ads_SDK.GADInterstitialAd
import cocoapods.Google_Mobile_Ads_SDK.GADInterstitialAdLoadCompletionHandler
import cocoapods.Google_Mobile_Ads_SDK.GADRequest
import cocoapods.Google_Mobile_Ads_SDK.GADRewardedAd
import cocoapods.Google_Mobile_Ads_SDK.GADRewardedAdLoadCompletionHandler
import cocoapods.Google_Mobile_Ads_SDK.GADRewardedInterstitialAd
import cocoapods.Google_Mobile_Ads_SDK.GADRewardedInterstitialAdLoadCompletionHandler
import cocoapods.Google_Mobile_Ads_SDK.GADUserDidEarnRewardHandler
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError
import platform.UIKit.UIApplication
import platform.darwin.NSObject

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
            completionHandler = object : GADInterstitialAdLoadCompletionHandler {
                override fun invoke(p1: GADInterstitialAd?, p2: NSError?) {
                    p1?.let {
                        Log.d(tag, "loadInterstitialAd:success")
                        interstitialAd = it
                        onLoaded()
                    }
                    p2?.let {
                        Log.e(tag, "loadInterstitialAd:failure:$it")
                        onFailedToLoad(it.code)
                    }
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
        val viewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        checkNotNull(viewController) { "Root ViewController is null" }

        interstitialAd?.let {
            interstitialAd?.fullScreenContentDelegate = object: NSObject(), GADFullScreenContentDelegateProtocol{
                override fun ad(
                    ad: GADFullScreenPresentingAdProtocol,
                    didFailToPresentFullScreenContentWithError: NSError
                ) {
                    // super.ad(ad, didFailToPresentFullScreenContentWithError)
                    Log.d(tag, "showInterstitialAd:ad called")
                    onShown()
                }

                override fun adDidDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
                    // super.adDidDismissFullScreenContent(ad)
                    Log.d(tag, "showInterstitialAd:dismissed")
                    interstitialAd = null
                    onDismissed()
                }

                override fun adDidRecordClick(ad: GADFullScreenPresentingAdProtocol) {
                    // super.adDidRecordClick(ad)
                    Log.d(tag, "showInterstitialAd:recorded click")
                    onClick()
                }

                override fun adDidRecordImpression(ad: GADFullScreenPresentingAdProtocol) {
                    // super.adDidRecordImpression(ad)
                    Log.d(tag, "showInterstitialAd:recorded impression")
                    onImpression()
                }

                override fun adWillDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
                    // super.adWillDismissFullScreenContent(ad)
                    Log.d(tag, "showInterstitialAd:will dismiss")
                }

                override fun adWillPresentFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
                    // super.adWillPresentFullScreenContent(ad)
                    Log.d(tag, "showInterstitialAd:will present content")
                }
            }
            interstitialAd?.presentFromRootViewController(viewController)
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
            completionHandler = object : GADRewardedInterstitialAdLoadCompletionHandler {
                override fun invoke(p1: GADRewardedInterstitialAd?, p2: NSError?) {
                    p1?.let {
                        Log.d(tag, "loadRewardedInterstitialAd:success")
                        rewardedInterstitialAd = it
                        onLoaded()
                    }
                    p2?.let {
                        Log.e(tag, "loadRewardedInterstitialAd:failure:$it")
                        onFailedToLoad(it.code)
                    }
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
        val viewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        checkNotNull(viewController) { "Root ViewController is null" }

        rewardedInterstitialAd?.let {
            rewardedInterstitialAd?.fullScreenContentDelegate = object: NSObject(), GADFullScreenContentDelegateProtocol{
                override fun ad(
                    ad: GADFullScreenPresentingAdProtocol,
                    didFailToPresentFullScreenContentWithError: NSError
                ) {
                    // super.ad(ad, didFailToPresentFullScreenContentWithError)
                    Log.d(tag, "showRewardedInterstitialAd:ad called")
                    onShown()
                }

                override fun adDidDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
                    // super.adDidDismissFullScreenContent(ad)
                    Log.d(tag, "showRewardedInterstitialAd:dismissed")
                    rewardedInterstitialAd = null
                    onDismissed()
                }

                override fun adDidRecordClick(ad: GADFullScreenPresentingAdProtocol) {
                    // super.adDidRecordClick(ad)
                    Log.d(tag, "showRewardedInterstitialAd:recorded click")
                    onClick()
                }

                override fun adDidRecordImpression(ad: GADFullScreenPresentingAdProtocol) {
                    // super.adDidRecordImpression(ad)
                    Log.d(tag, "showRewardedInterstitialAd:recorded impression")
                    onImpression()
                }

                override fun adWillDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
                    // super.adWillDismissFullScreenContent(ad)
                    Log.d(tag, "showRewardedInterstitialAd:will dismiss")
                }

                override fun adWillPresentFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
                    // super.adWillPresentFullScreenContent(ad)
                    Log.d(tag, "showRewardedInterstitialAd:will present content")
                }
            }
            rewardedInterstitialAd?.presentFromRootViewController(
                viewController = viewController,
                userDidEarnRewardHandler = object: GADUserDidEarnRewardHandler{
                    override fun invoke() {
                        onRewardEarned()
                    }

                }
            )
        } ?: Log.d(tag, "The interstitial ad wasn't ready yet.")
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
            completionHandler = object : GADRewardedAdLoadCompletionHandler {
                override fun invoke(p1: GADRewardedAd?, p2: NSError?) {
                    p1?.let {
                        Log.d(tag, "loadRewardedAd:success")
                        rewardedAd = it
                        onLoaded()
                    }
                    p2?.let {
                        Log.e(tag, "loadRewardedAd:failure:$it")
                        onFailedToLoad(it.code)
                    }
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
        val viewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        checkNotNull(viewController) { "Root ViewController is null" }

        rewardedAd?.let {
            rewardedAd?.fullScreenContentDelegate = object: NSObject(), GADFullScreenContentDelegateProtocol{
                override fun ad(
                    ad: GADFullScreenPresentingAdProtocol,
                    didFailToPresentFullScreenContentWithError: NSError
                ) {
                    // super.ad(ad, didFailToPresentFullScreenContentWithError)
                    Log.d(tag, "showRewardedAd:ad called")
                    onShown()
                }

                override fun adDidDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
                    // super.adDidDismissFullScreenContent(ad)
                    Log.d(tag, "showRewardedAd:dismissed")
                    rewardedAd = null
                    onDismissed()
                }

                override fun adDidRecordClick(ad: GADFullScreenPresentingAdProtocol) {
                    // super.adDidRecordClick(ad)
                    Log.d(tag, "showRewardedAd:recorded click")
                    onClick()
                }

                override fun adDidRecordImpression(ad: GADFullScreenPresentingAdProtocol) {
                    // super.adDidRecordImpression(ad)
                    Log.d(tag, "showRewardedAd:recorded impression")
                    onImpression()
                }

                override fun adWillDismissFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
                    // super.adWillDismissFullScreenContent(ad)
                    Log.d(tag, "showRewardedAd:will dismiss")
                }

                override fun adWillPresentFullScreenContent(ad: GADFullScreenPresentingAdProtocol) {
                    // super.adWillPresentFullScreenContent(ad)
                    Log.d(tag, "showRewardedAd:will present content")
                }
            }
            rewardedAd?.presentFromRootViewController(
                rootViewController = viewController,
                userDidEarnRewardHandler = object: GADUserDidEarnRewardHandler{
                    override fun invoke() {
                        onRewardEarned()
                    }

                }
            )
        } ?: Log.d(tag, "The interstitial ad wasn't ready yet.")
    }

}