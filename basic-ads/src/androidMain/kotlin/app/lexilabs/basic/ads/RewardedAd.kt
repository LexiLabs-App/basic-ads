package app.lexilabs.basic.ads

import android.app.Activity
import app.lexilabs.basic.logging.Log
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.AdRequest as AndroidAdRequest
import com.google.android.gms.ads.rewarded.RewardedAd as AndroidRewardedAd

public actual class RewardedAd actual constructor(private val activity: Any?) {

    private val tag = "RewardedAd"
    private var rewardedAd: AndroidRewardedAd? = null

    public actual fun load(
        adUnitId: String,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit
    ){
        Log.d(tag, "loadRewardedAd: Loading")
        require(activity != null) {
            "Activity Context must be set to non-null value in Android"
        }
        require(activity is Activity) {
            "activity variable must be of the Android `Activity` type"
        }
        AndroidRewardedAd.load(
            activity,
            adUnitId,
            AndroidAdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    Log.d(tag, "loadRewardedAd:failure:$adError")
                    onFailure(AdException(adError.message))
                }

                override fun onAdLoaded(ad: AndroidRewardedAd) {
                    super.onAdLoaded(ad)
                    Log.d(tag, "loadRewardedAd:success")
                    rewardedAd = ad
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
    ){
        Log.d(tag, "setListeners: Loading")
        require(rewardedAd != null) {
            "RewardedAd not loaded yet. `RewardedAd.load()` must be called first"
        }
        rewardedAd?.let {
            rewardedAd?.fullScreenContentCallback = FullscreenContentDelegate(
                onClick = onClick,
                onDismissed = onDismissed,
                onFailure = onFailure,
                onImpression = onImpression,
                onShown = onShown
            )
        } ?: Log.d(tag, "The rewarded ad wasn't ready yet.")
    }

    public actual fun show(
        onRewardEarned: () -> Unit
    ){
        Log.d(tag, "show: Loading")
        require(activity != null) {
            "Activity Context must be set to non-null value in Android"
        }
        require(activity is Activity) {
            "activity variable must be of the Android `Activity` type"
        }
        require(rewardedAd != null) {
            "RewardedAd not loaded yet. `RewardedAd.load()` must be called first"
        }
        rewardedAd?.show(activity) {
            Log.d(tag, "A reward was earned")
            onRewardEarned()
        }
    }
}