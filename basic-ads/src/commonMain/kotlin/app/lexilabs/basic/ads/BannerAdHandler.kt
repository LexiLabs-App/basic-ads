package app.lexilabs.basic.ads

/**
 * A [BannerAdHandler] is a landscape ad that covers part of the host app screen.
 *
 * @param activity Android required Activity
 *
 * ```kotlin
 * // Instantiate
 * val ad = BannerAd(activity, size)
 * // Load the Ad
 * ad.load(
 *   adUnitId = "AD_UNIT_ID_GOES_HERE", // Banner Ad Unit ID from AdMob
 *   onLoad = {
 *   // ad.setListeners could go here instead
 *   },
 *   onFailure = {  } // Callback when ad fails to load
 * )
 * // (optional) Observe what happens to the ad after it's shown
 * ad.setListeners(
 *   onFailure = {}, // Callback when ad fails to display
 *   onDismissed = {}, // Callback when ad is dismissed
 *   onShown = {}, // Callback after ad is shown
 *   onImpression = {}, // Callback after the ad makes an impression
 *   onClick = {} // Callback on ad click
 * )
 * // Show the ad
 * ad.show()
 * ```
 *
 * @see load
 * @see setListeners
 */
@DependsOnGoogleMobileAds
public expect class BannerAdHandler(activity: Any?) {

    /**
     * Determines the [AdState] of the [BannerAdHandler]
     */
    public val state: AdState

    /**
     * Loads an Banner Ad.
     * Note: Make all calls to the Mobile Ads SDK on the main thread.
     *
     * To load an banner ad, call [BannerAdHandler.load] method
     * and pass in an [AdUnitId] as a [String] and an [AdSize] to receive the loaded ad.
     * @param adUnitId Your Banner Ad AdUnitId [String] from AdMob
     * @param adSize Your Banner Ad [AdSize], which defaults to [AdSize.FULL_BANNER]
     * @see [AdUnitId.autoSelect]
     * @see [AdUnitId.BANNER_DEFAULT]
     * @see [AdSize.FULL_BANNER]
     */
    public fun load(
        adUnitId: String = AdUnitId.BANNER_DEFAULT,
        adSize: AdSize = AdSize.FULL_BANNER,
    )

    /**
     * Sets the callbacks for the Banner Ad.
     *
     * The [setListeners] function handles events related to displaying your
     * [BannerAdHandler]. Callbacks must be set before showing an Ad.
     *
     * @param onLoad Callback after the ad loads
     * @param onFailure Callback with [Exception] when ad fails to display
     * @param onDismissed Callback when ad is dismissed
     * @param onShown Callback after ad is shown
     * @param onImpression Callback after the ad makes an impression
     * @param onClick Callback on ad click
     *
     */
    public fun setListeners(
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit,
        onDismissed: () -> Unit,
        onShown: () -> Unit = {},
        onImpression: () -> Unit = {},
        onClick: () -> Unit = {}
    )
}