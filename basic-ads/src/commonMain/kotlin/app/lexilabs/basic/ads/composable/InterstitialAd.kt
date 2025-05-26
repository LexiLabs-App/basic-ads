package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds
import app.lexilabs.basic.ads.InterstitialAd

/**
 * Loads and displays an Interstitial Ad using a [Composable].
 * @param activity the current Activity (only needed for Android Impl)
 * @param adUnitId Your AdMob AdUnitId [String]
 * @param onDismissed Lambda that executes when the user closes the ad
 * @param onShown Lambda expression that executes after the ad is presented
 * @param onImpression Lambda expression that executes after the user has seen the ad
 * @param onClick Lambda expression that executes after the user clicks the ad
 * @param onFailure Lambda expression that executes after the ad fails to load or redirect
 * @param onLoad Lambda expression that executes after the [InterstitialAd] has fully loaded
 * @see AdUnitId.autoSelect
 */
@DependsOnGoogleMobileAds
@Composable
public fun InterstitialAd(
    activity: Any?,
    adUnitId: String = AdUnitId.INTERSTITIAL_DEFAULT,
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
    onLoad: () -> Unit = {}
) {
    val ad by rememberInterstitialAd(activity)
    ad.load(
        adUnitId = adUnitId,
        onLoad = onLoad,
        onFailure = onFailure
    )
    ad.setListeners(
        onFailure = onFailure,
        onDismissed = onDismissed,
        onShown = onShown,
        onImpression = onImpression,
        onClick = onClick
    )
    ad.show()
}

/**
 * Displays a pre-loaded Interstitial Ad using a [Composable].
 * @param loadedAd an [InterstitialAd] pre-loaded before the call
 * @param onDismissed Lambda that executes when the user closes the ad
 * @param onShown Lambda expression that executes after the ad is presented
 * @param onImpression Lambda expression that executes after the user has seen the ad
 * @param onClick Lambda expression that executes after the user clicks the ad
 * @param onFailure Lambda expression that executes after the ad fails to load or redirect
 * @see AdUnitId.autoSelect
 * @see InterstitialAd.load
 */
@DependsOnGoogleMobileAds
@Composable
public fun InterstitialAd(
    loadedAd: InterstitialAd,
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
) {
    loadedAd.setListeners(
        onFailure = onFailure,
        onDismissed = onDismissed,
        onShown = onShown,
        onImpression = onImpression,
        onClick = onClick
    )
    loadedAd.show()
}