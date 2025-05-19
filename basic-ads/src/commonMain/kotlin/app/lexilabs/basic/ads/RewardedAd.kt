package app.lexilabs.basic.ads

/**
 * An [RewardedAd] is a full-screen ad that cover the interface of the host app.
 * They're typically displayed at natural transition points in the flow of an app,
 * such as between activities or during the pause between levels in a game.
 * When an app shows an rewarded ad, the user has the choice to either tap on
 * the ad and continue to its destination or close it and return to the app.
 *
 * @param activity Android required Activity
 *
 * ```kotlin
 * // Instantiate
 * val ad = RewardedAd(activity)
 * // Load the Ad
 * ad.load(
 *   adUnitId = "AD_UNIT_ID_GOES_HERE", // Rewarded Ad Unit ID from AdMob
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
 * @see show
 */
public expect class RewardedAd(activity: Any?) {

    /**
     * Loads an Rewarded Ad.
     * Note: Make all calls to the Mobile Ads SDK on the main thread.
     *
     * To load an Rewarded ad, call [RewardedAd.load] method
     * and pass in an [AdUnitId] as a [String] to receive the loaded ad, the [onLoad]
     * callback, and any possible [Exception] from the [onFailure] callback.
     * @param adUnitId Your Rewarded Ad AdUnitId [String] from AdMob
     * @param onLoad Callback after the ad loads
     * @param onFailure Callback sharing the [Exception] when the ad fail to load
     * @see [AdUnitId.autoSelect]
     * @see [AdUnitId.REWARDED_DEFAULT]
     */
    public fun load(
        adUnitId: String = AdUnitId.REWARDED_DEFAULT,
        onLoad: () -> Unit,
        onFailure: (Exception) -> Unit
    )

    /**
     * Sets the FullScreenContentCallback for the Rewarded Ad.
     *
     * The [setListeners] function handles events related to displaying your
     * [RewardedAd]. Callbacks must be set before calling [RewardedAd.show].
     *
     * @param onFailure Callback with [Exception] when ad fails to display
     * @param onDismissed Callback when ad is dismissed
     * @param onShown Callback after ad is shown
     * @param onImpression Callback after the ad makes an impression
     * @param onClick Callback on ad click
     *
     */
    public fun setListeners(
        onFailure: (Exception) -> Unit,
        onDismissed: () -> Unit,
        onShown: () -> Unit = {},
        onImpression: () -> Unit = {},
        onClick: () -> Unit = {}
    )

    /**
     * Shows the [RewardedAd].
     *
     * @param onRewardEarned Callback when ad reward is earned
     */
    public fun show(
        onRewardEarned: () -> Unit
    )
}