package app.lexilabs.basic.ads

import android.app.Activity
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import app.lexilabs.basic.logging.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

public typealias BannerView = AdView

public actual class BannerAdHandler actual constructor(activity: Any?) {

    private val tag = "BannerAd"
    private val context: Context
    public var bannerView: BannerView? = null
    private val _state: MutableState<AdState> = mutableStateOf(AdState.NONE)
    private val _adSize: MutableState<AdSize> = mutableStateOf(AdSize.FULL_BANNER)

    /**
     * Determines the [AdState] of the [BannerAdHandler]
     */
    public actual val state: AdState by _state

    /**
     * Holds the active [AdSize] of the [BannerAdHandler]
     */
    public actual val adSize: AdSize by _adSize

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
    }

    @RequiresPermission("android.permission.INTERNET")
    public actual fun load(
        adUnitId: String,
        adSize: AdSize,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit,
        onDismissed: () -> Unit,
        onShown: () -> Unit,
        onImpression: () -> Unit,
        onClick: () -> Unit
    ) {
        _adSize.value = adSize
        _state.value = AdState.LOADING
        Log.d(tag, "loadBannerAd: Loading")
        bannerView = BannerView(context)
        bannerView?.apply {
            this.adUnitId = adUnitId
            this.setAdSize(adSize.toAndroid())
            this.adListener = BannerAdListener(
                onLoad = {
                    _state.value = AdState.READY
                    onLoad()
                },
                onFailure = {
                    _state.value = AdState.FAILING
                    onFailure(it)
                },
                onDismissed = {
                    _state.value = AdState.DISMISSED
                    onDismissed()
                },
                onShown = {
                    _state.value = AdState.SHOWN
                    onShown()
                },
                onImpression = {
                    _state.value = AdState.SHOWING
                    onImpression()
                },
                onClick = {
                    onClick()
                }
            )
            this.loadAd(AdRequest.Builder().build())
        }
    }
}