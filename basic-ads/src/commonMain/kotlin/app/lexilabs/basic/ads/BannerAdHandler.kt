package app.lexilabs.basic.ads

/**
 * A handler for creating and managing banner ads that cover part of the host app screen.
 *
 * This class is responsible for loading and managing the state of a banner advertisement
 * provided by Google Mobile Ads. It requires a platform-specific activity or context for its operations.
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
 * @param activity The platform-specific activity or context required for displaying the ad (e.g., an Android `Activity`).
 */
@DependsOnGoogleMobileAds
public expect class BannerAdHandler(activity: Any?) {

    /** The current [AdState] of the banner ad. */
    public val state: AdState

    /** The active [AdSize] of the banner ad. */
    public val adSize: AdSize

    /**
     * Loads a banner ad.
     * Note: Make all calls to the Mobile Ads SDK on the main thread.
     *
     * To load a banner ad, call the [load] method and provide an ad unit ID and ad size.
     *
     * @param adUnitId The ad unit ID for the banner ad.
     * @param adSize The size of the banner ad.
     * @param onLoad A callback invoked when the ad has finished loading.
     * @param onFailure A callback invoked with an [Exception] when the ad fails to load or display.
     * @param onDismissed A callback invoked when the ad is dismissed.
     * @param onShown A callback invoked when the ad is shown on screen.
     * @param onImpression A callback invoked when an ad impression has been recorded.
     * @param onClick A callback invoked when the user clicks on the ad.
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