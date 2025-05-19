package app.lexilabs.basic.ads

import android.annotation.SuppressLint
import android.app.Activity
import androidx.annotation.RequiresPermission
import app.lexilabs.basic.logging.Log
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.google.android.gms.ads.AdRequest as AndroidAdRequest
import com.google.android.gms.ads.interstitial.InterstitialAd as AndroidInterstitialAd
import com.google.android.gms.ads.rewarded.RewardedAd as AndroidRewardedAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd as AndroidRewardedInterstitialAd

public actual typealias AdRequest = com.google.android.gms.ads.AdRequest

public actual class AdLoader {

    private val tag: String = "AdLoader"
    private var interstitialAd: AndroidInterstitialAd? = null
    private var interstitialAdUnitId: String = ""
    private var rewardedInterstitialAd: AndroidRewardedInterstitialAd? = null
    private var rewardedInterstitialAdUnitId: String = ""
    private var rewardedAd: AndroidRewardedAd? = null
    private var rewardedAdUnitId: String = ""

    @RequiresPermission("android.permission.INTERNET")
    public actual fun requestAd(): AdRequest = AndroidAdRequest.Builder().build()

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
        require(activity is Activity) {
            "activity variable must be of the Android `Activity` type"
        }
        Log.d(tag, "loadInterstitialAd: Loading")
        interstitialAdUnitId = adUnitId
        AndroidInterstitialAd.load(
            activity,
            adUnitId,
            requestAd(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    Log.d(tag, "loadInterstitialAd:failure:$adError")
                    onFailedToLoad(adError.code.toLong())
                }

                override fun onAdLoaded(ad: AndroidInterstitialAd) {
                    super.onAdLoaded(ad)
                    Log.d(tag, "loadInterstitialAd:success")
                    interstitialAd = ad
                    onLoaded()
                }
            }
        )
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
        require(activity is Activity) {
            "activity variable must be of the Android `Activity` type"
        }
        interstitialAd?.let {
            interstitialAd?.fullScreenContentCallback = FullscreenContentDelegate(
                onClick = onClick,
                onDismissed = onDismissed,
                onFailure = { onFailure() },
                onImpression = onImpression,
                onShown = onShown
            )
            // CONTINUE
            interstitialAd?.show(activity)
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
        require(activity is Activity) {
            "activity variable must be of the Android `Activity` type"
        }
        rewardedInterstitialAdUnitId = adUnitId
        AndroidRewardedInterstitialAd
            .load(
                activity,
                adUnitId,
                requestAd(),
                object : RewardedInterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        super.onAdFailedToLoad(adError)
                        Log.d(tag, "loadRewardedInterstitialAd:failure:$adError")
                        onFailedToLoad(adError.code.toLong())
                    }

                    override fun onAdLoaded(ad: AndroidRewardedInterstitialAd) {
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
        require(activity is Activity) {
            "activity variable must be of the Android `Activity` type"
        }
        rewardedInterstitialAd?.let {
            rewardedInterstitialAd?.fullScreenContentCallback = FullscreenContentDelegate(
                onClick = onClick,
                onDismissed = onDismissed,
                onFailure = { onFailure() },
                onImpression = onImpression,
                onShown = onShown
            )
            // CONTINUE
            rewardedInterstitialAd?.show(activity) { onRewardEarned() }
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
        require(activity is Activity) {
            "activity variable must be of the Android `Activity` type"
        }
        rewardedAdUnitId = adUnitId
        com.google.android.gms.ads.rewarded.RewardedAd
            .load(
                activity,
                adUnitId,
                requestAd(),
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
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

//    @SuppressLint("MissingPermission")
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
        require(activity is Activity) {
            "activity variable must be of the Android `Activity` type"
        }
        rewardedAd?.let {
            rewardedAd?.fullScreenContentCallback = FullscreenContentDelegate(
                onClick = onClick,
                onDismissed = onDismissed,
                onFailure = { onFailure() },
                onImpression = onImpression,
                onShown = onShown
            )
            // CONTINUE
            rewardedAd?.show(activity) { onRewardEarned() }
        } ?: Log.d(tag, "The rewarded ad wasn't ready yet.")
    }
}