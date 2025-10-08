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

/**
 * A typealias for [AdView] on Android.
 */
public typealias BannerView = AdView

/**
 * A handler for banner ads on Android.
 *
 * @param activity The activity context.
 */
public actual class BannerAdHandler actual constructor(activity: Any?) {

    private val tag = "BannerAd"
    private val context: Context
    /** The underlying [BannerView] instance. */
    public var bannerView: BannerView? = null
    private val _state: MutableState<AdState> = mutableStateOf(AdState.NONE)
    private val _adSize: MutableState<AdSize> = mutableStateOf(AdSize.FULL_BANNER)

    /**
     * Determines the [AdState] of the [BannerAdHandler].
     */
    public actual val state: AdState by _state

    /**
     * Holds the active [AdSize] of the [BannerAdHandler].
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

    /**
     * Loads a banner ad.
     *
     * @param adUnitId The ad unit ID.
     * @param adSize The size of the ad.
     * @param onLoad A callback invoked when the ad is loaded.
     * @param onFailure A callback invoked when the ad fails to load.
     * @param onDismissed A callback invoked when the ad is dismissed.
     * @param onShown A callback invoked when the ad is shown.
     * @param onImpression A callback invoked when an impression is recorded for the ad.
     * @param onClick A callback invoked when the ad is clicked.
     */
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