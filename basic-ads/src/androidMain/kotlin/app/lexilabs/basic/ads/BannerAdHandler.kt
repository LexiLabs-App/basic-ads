package app.lexilabs.basic.ads

import android.app.Activity
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import app.lexilabs.basic.logging.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError

public typealias BannerView = com.google.android.gms.ads.AdView

public actual class BannerAdHandler actual constructor(activity: Any?) {

    private val tag = "BannerAd"
    private val context: Context
    public var bannerView: BannerView

    private val _state: MutableState<AdState> = mutableStateOf(AdState.NONE)

    /**
     * Determines the [AdState] of the [BannerAdHandler]
     */
    public actual val state: AdState by _state

    init {
        require(activity != null) {
            _state.value = AdState.FAILING
            "Android BannerAds requires Activity Context to be a non-null"
        }
        require(activity is Activity) {
            _state.value = AdState.FAILING
            "`activity` variable must be of the Android `Activity` or `Context` type"
        }
        context = activity
        bannerView = BannerView(context)
    }

    @RequiresPermission("android.permission.INTERNET")
    public actual fun load(
        adUnitId: String,
        adSize: AdSize,
    ) {
        _state.value = AdState.LOADING
        Log.d(tag, "loadBannerAd: Loading")
        bannerView.apply {
            this.adUnitId = adUnitId
            this.setAdSize(adSize.toAndroid())
            this.loadAd(AdRequest.Builder().build())
        }
    }

    public actual fun setListeners(
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit,
        onDismissed: () -> Unit,
        onShown: () -> Unit,
        onImpression: () -> Unit,
        onClick: () -> Unit
    ) {
        Log.d(tag, "setListeners: Loading")
        bannerView.adListener = object: AdListener() {
            override fun onAdClicked() {
                super.onAdClicked()
                onClick()
            }

            override fun onAdClosed() {
                super.onAdClosed()
                _state.value = AdState.DISMISSED
                onDismissed()
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                super.onAdFailedToLoad(adError)
                _state.value = AdState.FAILING
                onFailure(AdException(adError.message))
            }

            override fun onAdImpression() {
                super.onAdImpression()
                onImpression()
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                _state.value = AdState.READY
                onLoad()
            }

            override fun onAdOpened() {
                super.onAdOpened()
                _state.value = AdState.SHOWING
                onShown()
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        }
    }
}