package app.lexilabs.basic.ads

/**
 * A [BannerAdHandler] creates landscape ads that cover part of the host app screen.
 *
 * This class is responsible for loading and managing the state of a banner advertisement
 * provided by Google Mobile Ads. It requires an Android `Activity` for its operations.
 *
 * **Usage Example:**
 *
 * ```kotlin
 * // In your Composable function or where you have access to an Activity:
 * val activity = LocalContext.current as Activity
 *
 * // Instantiate the BannerAdHandler
 * val adHandler = rememberBannerAd(activity = activity)
 *
 * // Load the Ad (typically in a LaunchedEffect or similar)
 * LaunchedEffect(Unit) {
 *   adHandler.load(
 *     adUnitId = "YOUR_ADMOB_BANNER_AD_UNIT_ID", // Replace with your actual Ad Unit ID
 *     adSize = AdSize.BANNER, // Or any other desired AdSize
 *     onLoad = {
 *       // Ad loaded successfully
 *       println("Banner Ad loaded!")
 *     },
 *     onFailure = { exception ->
 *       // Ad failed to load
 *       println("Banner Ad failed to load: ${exception.message}")
 *     },
 *     onDismissed = {
 *       // Ad was dismissed (not typical for banners, but callback exists)
 *       println("Banner Ad dismissed.")
 *     },
 *     onShown = {
 *       // Ad is now visible on screen
 *       println("Banner Ad shown.")
 *     },
 *     onImpression = {
 *       // Ad impression has been recorded
 *       println("Banner Ad impression recorded.")
 *     },
 *     onClick = {
 *       // User clicked on the ad
 *       println("Banner Ad clicked.")
 *     }
 *   )
 * }
 *
 * // Display the ad in your Composable UI
 * if (adHandler.state == AdState.Loaded) { // Or handle other states like Loading, Error
 *   BannerAd(adHandler)
 * }
 * ```
 *
 * @param activity The Android `Activity` required for displaying the ad.
 *                 On platforms other than Android, this parameter might be unused or expect a platform-specific equivalent.
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