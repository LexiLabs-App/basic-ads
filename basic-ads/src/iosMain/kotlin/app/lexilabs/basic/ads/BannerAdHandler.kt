package app.lexilabs.basic.ads

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import app.lexilabs.basic.logging.Log
import cocoapods.Google_Mobile_Ads_SDK.GADBannerView
import cocoapods.Google_Mobile_Ads_SDK.GADRequest
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
public typealias BannerView = GADBannerView

@OptIn(ExperimentalForeignApi::class)
public actual class BannerAdHandler actual constructor(activity: Any?) {

    private val tag = "BannerAd"
    public var bannerView: BannerView
    private var delegate: BannerViewDelegate? = null

    private val _state: MutableState<AdState> = mutableStateOf(AdState.NONE)
    /**
     * Determines the [AdState] of the [BannerAdHandler]
     */
    public actual val state: AdState by _state

    init {
        bannerView = BannerView()
    }
    public actual fun load(
        adUnitId: String,
        adSize: AdSize,
    ) {
        _state.value = AdState.LOADING
        Log.d(tag, "load:starting")

        val viewController = getCurrentViewController() //UIApplication.sharedApplication.keyWindow?.rootViewController
        checkNotNull(viewController) { "Root ViewController is null" }

        bannerView = BannerView(adSize.toCGRectCValue()).apply {
            setAdUnitID(adUnitId)
            loadRequest(GADRequest())
            rootViewController = viewController
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
        Log.d(tag, "setListeners:starting")
        require(bannerView.adUnitID != null) {
            _state.value = AdState.FAILING
            "BannerAd not loaded yet. `BannerAd.load()` must be called first to pass the AdUnitId."
        }
        delegate = BannerViewDelegate(
            onLoad = onLoad,
            onClick = onClick,
            onWillDismiss = {},
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
        bannerView.delegate = delegate
    }
}