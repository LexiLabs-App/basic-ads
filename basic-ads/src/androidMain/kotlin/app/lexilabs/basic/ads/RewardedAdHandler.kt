package app.lexilabs.basic.ads

import android.app.Activity
import app.lexilabs.basic.logging.Log
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.AdRequest as AndroidAdRequest
import com.google.android.gms.ads.rewarded.RewardedAd as AndroidRewardedAd

public actual class RewardedAdHandler actual constructor(private val activity: Any?) {

    private val tag = "RewardedAd"
    private var rewardedAd: AndroidRewardedAd? = null

    public actual var state: AdState = AdState.NONE

    public actual fun load(
        adUnitId: String,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit
    ){
        state = AdState.LOADING
        Log.d(tag, "loadRewardedAd: Loading")
        require(activity != null) {
            state = AdState.FAILING
            "Activity Context must be set to non-null value in Android"
        }
        require(activity is Activity) {
            state = AdState.FAILING
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
                    state = AdState.FAILING
                    onFailure(AdException(adError.message))
                }

                override fun onAdLoaded(ad: AndroidRewardedAd) {
                    super.onAdLoaded(ad)
                    Log.d(tag, "loadRewardedAd:success")
                    rewardedAd = ad
                    state = AdState.READY
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
            state = AdState.FAILING
            "RewardedAd not loaded yet. `RewardedAd.load()` must be called first"
        }
        rewardedAd?.let {
            rewardedAd?.fullScreenContentCallback = FullscreenContentDelegate(
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
        } ?: Log.d(tag, "The rewarded ad wasn't ready yet.")
    }

    public actual fun show(
        onRewardEarned: () -> Unit
    ){
        state = AdState.SHOWING
        Log.d(tag, "show: Loading")
        require(activity != null) {
            state = AdState.FAILING
            "Activity Context must be set to non-null value in Android"
        }
        require(activity is Activity) {
            state = AdState.FAILING
            "activity variable must be of the Android `Activity` type"
        }
        require(rewardedAd != null) {
            state = AdState.FAILING
            "RewardedAd not loaded yet. `RewardedAd.load()` must be called first"
        }
        rewardedAd?.show(activity) {
            Log.d(tag, "A reward was earned")
            onRewardEarned()
        }
    }
}