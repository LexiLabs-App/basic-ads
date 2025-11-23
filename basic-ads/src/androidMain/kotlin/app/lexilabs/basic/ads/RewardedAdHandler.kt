package app.lexilabs.basic.ads

import android.app.Activity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import app.lexilabs.basic.logging.Log
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions
import com.google.android.gms.ads.AdRequest as AndroidAdRequest
import com.google.android.gms.ads.rewarded.RewardedAd as AndroidRewardedAd

/**
 * A handler for rewarded ads on Android.
 *
 * @param activity The activity context.
 */
@DependsOnGoogleMobileAds
public actual class RewardedAdHandler actual constructor(private val activity: Any?) {

    private val tag = "RewardedAd"
    private var rewardedAd: AndroidRewardedAd? = null
    private val _state: MutableState<AdState> = mutableStateOf(AdState.NONE)

    /** The current [AdState] of the rewarded ad. */
    public actual val state: AdState by _state

    /**
     * Loads a rewarded ad.
     *
     * @param adUnitId The ad unit ID.
     * @param onLoad A callback invoked when the ad is loaded.
     * @param onFailure A callback invoked when the ad fails to load.
     */
    public actual fun load(
        adUnitId: String,
        userId: String?,
        customData: String?,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
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
        AndroidRewardedAd.load(
            activity,
            adUnitId,
            AndroidAdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    Log.d(tag, "loadRewardedAd:failure:$adError")
                    _state.value = AdState.FAILING
                    onFailure(AdException(adError.message))
                }

                override fun onAdLoaded(ad: AndroidRewardedAd) {
                    super.onAdLoaded(ad)
                    Log.d(tag, "loadRewardedAd:success")
                    rewardedAd = ad
                    val options =
                        ServerSideVerificationOptions.Builder().apply {
                            if (userId != null) setUserId(userId)
                            if (customData != null) setCustomData(customData)
                        }.build()
                    rewardedAd?.setServerSideVerificationOptions(options)
                    _state.value = AdState.READY
                    onLoad()
                }
            }
        )
    }

    /**
     * Sets the listeners for the rewarded ad.
     *
     * @param onFailure A callback invoked when the ad fails to show.
     * @param onDismissed A callback invoked when the ad is dismissed.
     * @param onShown A callback invoked when the ad is shown.
     * @param onImpression A callback invoked when an impression is recorded for the ad.
     * @param onClick A callback invoked when the ad is clicked.
     */
    public actual fun setListeners(
        onFailure: (Exception) -> Unit,
        onDismissed: () -> Unit,
        onShown: () -> Unit,
        onImpression: () -> Unit,
        onClick: () -> Unit
    ) {
        Log.d(tag, "setListeners: Loading")
        require(rewardedAd != null) {
            _state.value = AdState.FAILING
            "RewardedAd not loaded yet. `RewardedAd.load()` must be called first"
        }
        rewardedAd?.let {
            rewardedAd?.fullScreenContentCallback = FullscreenContentDelegate(
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
        } ?: Log.d(tag, "The rewarded ad wasn't ready yet.")
    }

    /**
     * Shows the rewarded ad.
     *
     * @param onRewardEarned A callback invoked when the user earns a reward.
     */
    public actual fun show(
        onRewardEarned: (RewardItem) -> Unit
    ) {
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
        require(rewardedAd != null) {
            _state.value = AdState.FAILING
            "RewardedAd not loaded yet. `RewardedAd.load()` must be called first"
        }
        rewardedAd?.show(activity) { reward ->
            Log.d(tag, "A reward was earned")
            onRewardEarned(RewardItem(reward))
        }
    }
}
