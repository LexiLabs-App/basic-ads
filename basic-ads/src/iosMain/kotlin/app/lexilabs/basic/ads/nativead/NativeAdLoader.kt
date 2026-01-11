package app.lexilabs.basic.ads.nativead

import app.lexilabs.basic.ads.AdException
import app.lexilabs.basic.ads.getRootViewController
import app.lexilabs.basic.logging.Log
import cocoapods.Google_Mobile_Ads_SDK.GADAdLoader
import cocoapods.Google_Mobile_Ads_SDK.GADAdLoaderAdTypeNative
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAd
import cocoapods.Google_Mobile_Ads_SDK.GADNativeAdLoaderDelegateProtocol
import cocoapods.Google_Mobile_Ads_SDK.GADRequest
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.coroutines.resume

@OptIn(ExperimentalForeignApi::class)
public class NativeAdLoader(
    private val adUnitId: String,
    public val onLoad: () -> Unit,
    public val onFailure: (Exception) -> Unit,
    public val onDismissed: () -> Unit,
    public val onShown: () -> Unit,
    public val onImpression: () -> Unit,
    public val onClick: () -> Unit
): NSObject(), GADNativeAdLoaderDelegateProtocol {
    public var nativeAd: GADNativeAd? = null
    private var adLoader: GADAdLoader? = null
    private var continuation: ((Result<GADNativeAd>) -> Unit)? = null

    private val tag: String = "NativeAdLoader"

    @OptIn(ExperimentalCoroutinesApi::class)
    public suspend fun loadAd(): Result<GADNativeAd> = suspendCancellableCoroutine { cont ->
        // Store the continuation to be resumed later by the delegate
        this.continuation = { result ->
            if (cont.isActive) {
                cont.resume(result)
            }
        }

        // Get the root view controller to present the ad
        val rootViewController = getRootViewController()
        if (rootViewController == null) {
            continuation?.invoke(Result.failure(Exception("Root viewcontroller not found.")))
            return@suspendCancellableCoroutine
        }

        // Initialize and load the ad
        adLoader = GADAdLoader(
            adUnitID = adUnitId, // Test Ad Unit ID
            rootViewController = rootViewController,
            adTypes = listOf(GADAdLoaderAdTypeNative),
            options = null
        ).apply {
            delegate = this@NativeAdLoader
            loadRequest(GADRequest())
        }

        // Handle cancellation
        cont.invokeOnCancellation {
            adLoader?.delegate = null
            adLoader = null
            continuation = null
        }
    }

    override fun adLoader(
        adLoader: GADAdLoader,
        didReceiveNativeAd: GADNativeAd
    ) {
        Log.i(tag, "Ad Loaded")
        onLoad()
        this.nativeAd = didReceiveNativeAd
        this.nativeAd!!.delegate = NativeAdDelegate(
            onImpression = onImpression,
            onClick = onClick,
            onShown = onShown,
            onDismissed = onDismissed
        )
        continuation?.invoke(Result.success(didReceiveNativeAd))
    }

    override fun adLoader(
        adLoader: GADAdLoader,
        didFailToReceiveAdWithError: NSError
    ) {
        Log.e(tag, "failure: ${didFailToReceiveAdWithError.localizedDescription}")
        val error = AdException(didFailToReceiveAdWithError.localizedDescription)
        onFailure(error)
        continuation?.invoke(Result.failure(error))
    }

}