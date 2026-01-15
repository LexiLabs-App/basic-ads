package app.lexilabs.basic.ads.composable

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.uikit.LocalUIViewController
import androidx.compose.ui.viewinterop.UIKitView
import androidx.compose.ui.window.ComposeUIViewController
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.nativead.NativeAdHandler
import app.lexilabs.basic.ads.nativead.NativeAdTemplate
import app.lexilabs.basic.logging.Log
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.addChildViewController
import platform.UIKit.didMoveToParentViewController
import platform.UIKit.removeFromParentViewController
import platform.UIKit.willMoveToParentViewController

/**
 * A composable that displays a native ad.
 * @param nativeAdTemplate the composable that will be used to display the native ad
 * @param adUnitId the ad unit ID for the native ad
 * @param onDismissed a callback that will be invoked when the ad is dismissed
 * @param onShown a callback that will be invoked when the ad is shown
 * @param onImpression a callback that will be invoked when an impression is recorded for the ad
 * @param onClick a callback that will be invoked when the ad is clicked
 * @param onFailure a callback that will be invoked when the ad fails to load
 * @param onLoad a callback that will be invoked when the ad has loaded
 */
@DependsOnGoogleMobileAds
@Composable
public actual fun NativeAd(
    nativeAdTemplate: NativeAdTemplate,
    adUnitId: String,
    onDismissed: () -> Unit,
    onShown: () -> Unit,
    onImpression: () -> Unit,
    onClick: () -> Unit,
    onFailure: (Exception) -> Unit,
    onLoad: () -> Unit
) {
    val ad by rememberNativeAd(
        adUnitId = adUnitId,
        onLoad = onLoad,
        onFailure = onFailure,
        onDismissed = onDismissed,
        onShown = onShown,
        onImpression = onImpression,
        onClick = onClick
    )

    if (ad.state == AdState.READY) {
        NativeAd(loadedAd = ad, nativeAdTemplate = nativeAdTemplate)
    }
}

/**
 * A composable that displays a native ad.
 * @param activity The current activity. This is only needed for Android implementation.
 * @param nativeAdTemplate the composable that will be used to display the native ad
 * @param adUnitId the ad unit ID for the native ad
 * @param onDismissed a callback that will be invoked when the ad is dismissed
 * @param onShown a callback that will be invoked when the ad is shown
 * @param onImpression a callback that will be invoked when an impression is recorded for the ad
 * @param onClick a callback that will be invoked when the ad is clicked
 * @param onFailure a callback that will be invoked when the ad fails to load
 * @param onLoad a callback that will be invoked when the ad has loaded
 */
@DependsOnGoogleMobileAds
@Deprecated("The `activity` argument is no longer required as of v1.1.0-beta01")
@Composable
public actual fun NativeAd(
    activity: Any?,
    nativeAdTemplate: NativeAdTemplate,
    adUnitId: String,
    onDismissed: () -> Unit,
    onShown: () -> Unit,
    onImpression: () -> Unit,
    onClick: () -> Unit,
    onFailure: (Exception) -> Unit,
    onLoad: () -> Unit
) {
    val ad by rememberNativeAd(
        adUnitId = adUnitId,
        onLoad = onLoad,
        onFailure = onFailure,
        onDismissed = onDismissed,
        onShown = onShown,
        onImpression = onImpression,
        onClick = onClick
    )

    if (ad.state == AdState.READY) {
        NativeAd(loadedAd = ad, nativeAdTemplate = nativeAdTemplate)
    }
}

/**
 * A composable that displays a native ad.
 * @param loadedAd the pre-loaded native ad
 * @param nativeAdTemplate the composable that will be used to display the native ad
 */
@OptIn(ExperimentalForeignApi::class)
@DependsOnGoogleMobileAds
@Composable
public actual fun NativeAd(
    loadedAd: NativeAdHandler,
    nativeAdTemplate: NativeAdTemplate
) {
    Log.i("NativeAd", "Starting NativeAd")

    if (loadedAd.state != AdState.READY) {
        return
    }

    val adData = loadedAd.render()

    // By keying this composable against the ad handler, we ensure that if a new ad is loaded,
    // the entire UIKitView is recomposed, correctly displaying the new ad.
    key(loadedAd) {
        val parentController = LocalUIViewController.current

        // We create and remember a ComposeUIViewController to host our custom ad template.
        val adTemplateController = remember {
            ComposeUIViewController { nativeAdTemplate.copy(adData).Show() }
        }

        // This effect correctly manages the lifecycle of the child view controller.
        DisposableEffect(parentController, adTemplateController) {
            parentController.addChildViewController(adTemplateController)
            adTemplateController.didMoveToParentViewController(parentController)
            onDispose {
                adTemplateController.willMoveToParentViewController(null)
                adTemplateController.view.removeFromSuperview()
                adTemplateController.removeFromParentViewController()
            }
        }

        // The UIKitView now simply hosts the view from the ad template controller.
        // The template itself is now responsible for creating the GADNativeAdView.
        UIKitView(
            factory = {
                adTemplateController.view
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}