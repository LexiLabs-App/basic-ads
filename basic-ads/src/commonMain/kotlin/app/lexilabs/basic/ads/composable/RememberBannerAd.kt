package app.lexilabs.basic.ads.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import app.lexilabs.basic.ads.AdSize
import app.lexilabs.basic.ads.AdState
import app.lexilabs.basic.ads.AdUnitId
import app.lexilabs.basic.ads.BannerAdHandler
import app.lexilabs.basic.ads.DependsOnGoogleMobileAds

/**
 * A Composable function that remembers a [BannerAdHandler] across compositions.
 *
 * This function creates and manages a [BannerAdHandler] instance, ensuring it persists
 * across recompositions. It automatically attempts to load an ad when the ad's state
 * is [AdState.DISMISSED] or [AdState.NONE].
 *
 * **Note:** This Composable depends on the Google Mobile Ads SDK. Ensure that the
 * SDK is properly integrated into your project.
 *
 * @param activity The current Activity or Context. This is crucial for the ad to function correctly.
 *                 It's recommended to pass the result of `LocalContext.current as Activity`.
 * @param adUnitId The ad unit ID for the banner ad. Defaults to [AdUnitId.BANNER_DEFAULT].
 * @param adSize The size of the banner ad. Defaults to [AdSize.FULL_BANNER].
 * @param onLoad A callback invoked when the ad has successfully loaded.
 * @param onFailure A callback invoked when the ad fails to load. It provides an [Exception]
 *                  with details about the failure.
 * @param onDismissed A callback invoked when the ad is dismissed by the user.
 * @param onShown A callback invoked when the ad is shown on the screen.
 * @param onImpression A callback invoked when an impression is recorded for the ad.
 * @param onClick A callback invoked when the ad is clicked by the user.
 * @return A [MutableState] holding the [BannerAdHandler]. You can use this state to
 *         interact with the ad (e.g., to display it in your UI).
 */
@DependsOnGoogleMobileAds
@Composable
public fun rememberBannerAd(
    activity: Any?,
    adUnitId: String = AdUnitId.BANNER_DEFAULT,
    adSize: AdSize = AdSize.FULL_BANNER,
    onLoad: () -> Unit = {},
    onFailure: (Exception) -> Unit = {},
    onDismissed: () -> Unit = {},
    onShown: () -> Unit = {},
    onImpression: () -> Unit = {},
    onClick: () -> Unit = {}
): MutableState<BannerAdHandler> {
    val ad = remember(activity) { mutableStateOf(BannerAdHandler(activity)) }
    when(ad.value.state){
        AdState.DISMISSED,
        AdState.NONE -> {
            ad.value.load(
                adUnitId = adUnitId,
                adSize = adSize,
                onLoad = onLoad,
                onFailure = onFailure,
                onDismissed = onDismissed,
                onShown = onShown,
                onImpression = onImpression,
                onClick = onClick
            )
        }
        else -> { /** DO NOTHING **/ }
    }
    return ad
}