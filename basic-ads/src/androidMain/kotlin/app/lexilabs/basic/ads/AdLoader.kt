package app.lexilabs.basic.ads

import android.annotation.SuppressLint
import android.app.Activity
import androidx.annotation.RequiresPermission
import app.lexilabs.basic.logging.Log

public actual typealias AdRequest = com.google.android.gms.ads.AdRequest

public actual class AdLoader {

    private val tag: String = "AdLoader"
    private var interstitialAd: com.google.android.gms.ads.interstitial.InterstitialAd? = null
    private var interstitialAdUnitId: String = ""
    private var rewardedInterstitialAd: com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd? = null
    private var rewardedInterstitialAdUnitId: String = ""
    private var rewardedAd: com.google.android.gms.ads.rewarded.RewardedAd? = null
    private var rewardedAdUnitId: String = ""

    @RequiresPermission("android.permission.INTERNET")
    public actual fun requestAd(): AdRequest =
        com.google.android.gms.ads.AdRequest.Builder().build()

    @RequiresPermission("android.permission.INTERNET")
    public actual fun loadInterstitialAd(
        activity: Any?,
        adUnitId: String,
        onLoaded: () -> Unit,
        onFailedToLoad: (Long) -> Unit,
    ) {
        require(activity != null) {
            "Activity Context must be set to non-null value in Android"
        }
        Log.d(tag, "loadInterstitialAd: Loading")
        interstitialAdUnitId = adUnitId
        com.google.android.gms.ads.interstitial.InterstitialAd
            .load(
                activity as Activity,
                adUnitId,
                requestAd(),
                object : com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: com.google.android.gms.ads.LoadAdError) {
                super.onAdFailedToLoad(adError)
                Log.d(tag, "loadInterstitialAd:failure:$adError")
                onFailedToLoad(adError.code.toLong())
            }

            override fun onAdLoaded(ad: com.google.android.gms.ads.interstitial.InterstitialAd) {
                super.onAdLoaded(ad)
                Log.d(tag, "loadInterstitialAd:success")
                interstitialAd = ad
                onLoaded()
            }
        })
    }

    @SuppressLint("MissingPermission")
    @RequiresPermission("android.permission.INTERNET")
    public actual fun showInterstitialAd(
        activity: Any?,
        onDismissed: () -> Unit,
        onShown: () -> Unit,
        onImpression: () -> Unit,
        onClick: () -> Unit,
        onFailure: () -> Unit,
    ){
        require(activity != null) {
            "Activity Context must be set to non-null value in Android"
        }
//        if (interstitialAd != null) {
        interstitialAd?.let {
            interstitialAd?.fullScreenContentCallback = object: com.google.android.gms.ads.FullScreenContentCallback() {
                override fun onAdClicked() {
                    super.onAdClicked()
                    // Called when a click is recorded for an ad.
                    Log.d(tag, "Ad was clicked.")
                    loadInterstitialAd(activity, interstitialAdUnitId)
                    onClick()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    // Called when ad is dismissed.
                    Log.d(tag, "Ad dismissed fullscreen content.")
                    interstitialAd = null
                    loadInterstitialAd(activity, interstitialAdUnitId)
                    onDismissed()
                }

                override fun onAdFailedToShowFullScreenContent(p0: com.google.android.gms.ads.AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    // Called when ad fails to show.
                    Log.e(tag, "Ad failed to show fullscreen content.")
                    interstitialAd = null
                    onFailure()
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    // Called when an impression is recorded for an ad.
                    Log.d(tag, "Ad recorded an impression.")
                    onImpression()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    // Called when ad is shown.
                    Log.d(tag, "Ad showed fullscreen content.")
                    onShown()
                }
            }
            // CONTINUE
            (interstitialAd as com.google.android.gms.ads.interstitial.InterstitialAd).show(activity as Activity)
        } ?: Log.d(tag, "The interstitial ad wasn't ready yet.")
    }

    @RequiresPermission("android.permission.INTERNET")
    public actual fun loadRewardedInterstitialAd(
        activity: Any?,
        adUnitId: String,
        onLoaded: () -> Unit,
        onFailedToLoad: (Long) -> Unit,
    ) {
        require(activity != null) {
            "Activity Context must be set to non-null value in Android"
        }
        rewardedInterstitialAdUnitId = adUnitId
        com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
            .load(
                activity as Activity,
                adUnitId,
                requestAd(),
                object : com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: com.google.android.gms.ads.LoadAdError) {
                        super.onAdFailedToLoad(adError)
                        Log.d(tag, "loadRewardedInterstitialAd:failure:$adError")
                        onFailedToLoad(adError.code.toLong())
                    }

                    override fun onAdLoaded(ad: com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd) {
                        super.onAdLoaded(ad)
                        Log.d(tag, "loadRewardedInterstitialAd:success")
                        rewardedInterstitialAd = ad
                        onLoaded()
                    }
                })
    }

    @SuppressLint("MissingPermission")
    @RequiresPermission("android.permission.INTERNET")
    public actual fun showRewardedInterstitialAd(
        activity: Any?,
        onRewardEarned: () -> Unit,
        onDismissed: () -> Unit,
        onShown: () -> Unit,
        onImpression: () -> Unit,
        onClick: () -> Unit,
        onFailure: () -> Unit,
    ){
        require(activity != null) {
            "Activity Context must be set to non-null value in Android"
        }
        rewardedInterstitialAd?.let {
            rewardedInterstitialAd?.fullScreenContentCallback = object: com.google.android.gms.ads.FullScreenContentCallback() {
                override fun onAdClicked() {
                    super.onAdClicked()
                    // Called when a click is recorded for an ad.
                    Log.d(tag, "Ad was clicked.")
                    loadRewardedInterstitialAd(activity, rewardedInterstitialAdUnitId)
                    onClick()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    // Called when ad is dismissed.
                    Log.d(tag, "Ad dismissed fullscreen content.")
                    rewardedInterstitialAd = null
                    loadRewardedInterstitialAd(activity, rewardedInterstitialAdUnitId)
                    onDismissed()
                }

                override fun onAdFailedToShowFullScreenContent(p0: com.google.android.gms.ads.AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    // Called when ad fails to show.
                    Log.e(tag, "Ad failed to show fullscreen content.")
                    rewardedInterstitialAd = null
                    onFailure()
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    // Called when an impression is recorded for an ad.
                    Log.d(tag, "Ad recorded an impression.")
                    onImpression()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    // Called when ad is shown.
                    Log.d(tag, "Ad showed fullscreen content.")
                    onShown()
                }
            }
            // CONTINUE
            (rewardedInterstitialAd as com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd).show(
                activity as Activity
            ) {
                onRewardEarned()
            }
        } ?: Log.d(tag, "The rewarded interstitial ad wasn't ready yet.")
    }

    @RequiresPermission("android.permission.INTERNET")
    public actual fun loadRewardedAd(
        activity: Any?,
        adUnitId: String,
        onLoaded: () -> Unit,
        onFailedToLoad: (Long) -> Unit,
    ) {
        require(activity != null) {
            "Activity Context must be set to non-null value in Android"
        }
        rewardedAdUnitId = adUnitId
        com.google.android.gms.ads.rewarded.RewardedAd
            .load(
                activity as Activity,
                adUnitId,
                requestAd(),
                object : com.google.android.gms.ads.rewarded.RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: com.google.android.gms.ads.LoadAdError) {
                        super.onAdFailedToLoad(adError)
                        Log.d(tag, "loadRewardedInterstitialAd:failure:$adError")
                        onFailedToLoad(adError.code.toLong())
                    }

                    override fun onAdLoaded(ad: com.google.android.gms.ads.rewarded.RewardedAd) {
                        super.onAdLoaded(ad)
                        Log.d(tag, "loadRewardedInterstitialAd:success")
                        rewardedAd = ad
                        onLoaded()
                    }
                })
    }

    @SuppressLint("MissingPermission")
    @RequiresPermission("android.permission.INTERNET")
    public actual fun showRewardedAd(
        activity: Any?,
        onRewardEarned: () -> Unit,
        onDismissed: () -> Unit,
        onShown: () -> Unit,
        onImpression: () -> Unit,
        onClick: () -> Unit,
        onFailure: () -> Unit,
    ){
        require(activity != null) {
            "Activity Context must be set to non-null value in Android"
        }
        rewardedAd?.let {
            rewardedAd?.fullScreenContentCallback = object: com.google.android.gms.ads.FullScreenContentCallback() {
                override fun onAdClicked() {
                    super.onAdClicked()
                    // Called when a click is recorded for an ad.
                    Log.d(tag, "Ad was clicked.")
                    loadRewardedAd(activity, rewardedAdUnitId)
                    onClick()
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    // Called when ad is dismissed.
                    Log.d(tag, "Ad dismissed fullscreen content.")
                    rewardedAd = null
                    loadRewardedAd(activity, rewardedAdUnitId)
                    onDismissed()
                }

                override fun onAdFailedToShowFullScreenContent(p0: com.google.android.gms.ads.AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    // Called when ad fails to show.
                    Log.e(tag, "Ad failed to show fullscreen content.")
                    rewardedAd = null
                    onFailure()
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                    // Called when an impression is recorded for an ad.
                    Log.d(tag, "Ad recorded an impression.")
                    onImpression()
                }

                override fun onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent()
                    // Called when ad is shown.
                    Log.d(tag, "Ad showed fullscreen content.")
                    onShown()
                }
            }
            // CONTINUE
            (rewardedAd as com.google.android.gms.ads.rewarded.RewardedAd).show(
                activity as Activity
            ) {
                onRewardEarned()
            }
        } ?: Log.d(tag, "The rewarded ad wasn't ready yet.")
    }
}