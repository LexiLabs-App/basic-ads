package app.lexilabs.basic.ads

import app.lexilabs.basic.ads.composable.rememberBannerAd

/**
 * A [BannerAdHandler] creates landscape ads that cover part of the host app screen.
 *
 * @param activity Android required Activity
 *
 * ```kotlin
 * // Instantiate
 * val ad = BannerAdHandler(activity)
 * // Load the Ad
 * ad.load(
 *   adUnitId = "AD_UNIT_ID_GOES_HERE", // Banner Ad Unit ID from AdMob
 *   adSize = AdSize.FULL_BANNER // Banner AdSize goes here
 *   onFailure = {}, // Callback when ad fails to display
 *   onDismissed = {}, // Callback when ad is dismissed
 *   onShown = {}, // Callback after ad is shown
 *   onImpression = {}, // Callback after the ad makes an impression
 *   onClick = {} // Callback on ad click
 * )
 * // Show the ad in your Composable
 * BannerAd(ad)
 * ```
 * @see rememberBannerAd
 * @see load
 */
@DependsOnGoogleMobileAds
public expect class BannerAdHandler(activity: Any?) {

    /**
     * Determines the [AdState] of the [BannerAdHandler]
     */
    public val state: AdState

    /**
     * Holds the active [AdSize] of the [BannerAdHandler]
     */
    public val adSize: AdSize

    /**
     * Loads an Banner Ad.
     * Note: Make all calls to the Mobile Ads SDK on the main thread.
     *
     * To load an banner ad, call [BannerAdHandler.load] method
     * and pass in an [AdUnitId] as a [String] and an [AdSize] to receive the loaded ad.
     * @param adUnitId Your Banner Ad AdUnitId [String] from AdMob
     * @param adSize Your Banner Ad [AdSize], which defaults to [AdSize.FULL_BANNER]
     * @param onLoad Callback after the ad loads
     * @param onFailure Callback with [Exception] when ad fails to display
     * @param onDismissed Callback when ad is dismissed
     * @param onShown Callback after ad is shown
     * @param onImpression Callback after the ad makes an impression
     * @param onClick Callback on ad click
     * @see [AdUnitId.autoSelect]
     * @see [AdUnitId.BANNER_DEFAULT]
     * @see [AdSize.FULL_BANNER]
     */
    @Suppress("Unused Parameter")
    public fun load(
        adUnitId: String = AdUnitId.BANNER_DEFAULT,
        adSize: AdSize = AdSize.FULL_BANNER,
        onLoad: () -> Unit = {},
        onFailure: (Exception) -> Unit = {},
        onDismissed: () -> Unit = {},
        onShown: () -> Unit = {},
        onImpression: () -> Unit = {},
        onClick: () -> Unit = {}
    )
}