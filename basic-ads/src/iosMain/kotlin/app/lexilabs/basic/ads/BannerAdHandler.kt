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
    public var bannerView: BannerView = BannerView()
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
        Log.d(tag, "load:starting")

        val viewController = getCurrentViewController()
        checkNotNull(viewController) { "Root ViewController is null" }

        bannerView = BannerView(adSize.toCGRectCValue()).apply {
            setAdUnitID(adUnitId)
            delegate = BannerViewDelegate(
                onLoad = {
                    _state.value = AdState.READY
                    onLoad()
                },
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
                onImpression = {
                    _state.value = AdState.SHOWING
                    onImpression()
                },
                onShown = {
                    _state.value = AdState.SHOWN
                    onShown()
                }
            )
            loadRequest(GADRequest())
            rootViewController = viewController
        }
    }
}