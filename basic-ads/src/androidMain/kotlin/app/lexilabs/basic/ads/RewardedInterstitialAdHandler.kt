package app.lexilabs.basic.ads

import android.app.Activity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import app.lexilabs.basic.logging.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd as AndroidRewardedInterstitialAd

public actual class RewardedInterstitialAdHandler actual constructor(
    private val activity: Any?
) {

    private val tag = "RewardedInterstitialAd"
    private var rewardedInterstitialAd: AndroidRewardedInterstitialAd? = null
    private val _state: MutableState<AdState> = mutableStateOf(AdState.NONE)
    public actual val state: AdState by _state

    public actual fun load(
        adUnitId: String,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit
    ){
        _state.value = AdState.LOADING
        Log.d(tag, "loadRewardedAd: Loading")
        require(activity != null) {
            _state.value = AdState.FAILING
            "Activity Context must be set to non-null value in Android"
        }
        require(activity is Activity) {
            _state.value = AdState.FAILING
            "activity variable must be of the Android `Activity` type"
        }
        AndroidRewardedInterstitialAd.load(
            activity,
            adUnitId,
            AdRequest.Builder().build(),
            object : RewardedInterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    Log.d(tag, "loadRewardedAd:failure:$adError")
                    _state.value = AdState.FAILING
                    onFailure(AdException(adError.message))
                }

                override fun onAdLoaded(ad: AndroidRewardedInterstitialAd) {
                    super.onAdLoaded(ad)
                    Log.d(tag, "loadRewardedAd:success")
                    rewardedInterstitialAd = ad
                    _state.value = AdState.READY
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
    ) {
        Log.d(tag, "setListeners: Loading")
        require(rewardedInterstitialAd != null) {
            _state.value = AdState.FAILING
            "RewardedAd not loaded yet. `RewardedAd.load()` must be called first"
        }
        rewardedInterstitialAd?.let {
            rewardedInterstitialAd?.fullScreenContentCallback = FullscreenContentDelegate(
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
        } ?: Log.d(tag, "The rewarded interstitial ad wasn't ready yet.")
    }

    public actual fun show(onRewardEarned: () -> Unit) {
        _state.value = AdState.SHOWING
        Log.d(tag, "show: Loading")
        require(activity != null) {
            _state.value = AdState.FAILING
            "Activity Context must be set to non-null value in Android"
        }
        require(activity is Activity) {
            _state.value = AdState.FAILING
            "activity variable must be of the Android `Activity` type"
        }
        require(rewardedInterstitialAd != null) {
            _state.value = AdState.FAILING
            "RewardedAd not loaded yet. `RewardedAd.load()` must be called first"
        }
        rewardedInterstitialAd?.show(activity) {
            Log.d(tag, "A reward was earned")
            onRewardEarned()
        }
    }
}