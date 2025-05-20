package app.lexilabs.basic.ads

/**
 * Generates components required for ad requests
 */
@Deprecated(
    message = "Will be obsolete and deleted in Basic-Ads version 0.2.7",
    level = DeprecationLevel.WARNING
)
public expect class AdRequest

/**
 * Used to send an [AdRequest], hold loaded Ads, show fullscreen ads, and execute lambda expressions.
 * Most functions require an [Any] value containing an Android Activity or null for iOS.
 *
 * @see requestAd
 * @see loadInterstitialAd
 * @see showInterstitialAd
 * @see loadRewardedInterstitialAd
 * @see showRewardedInterstitialAd
 * @see loadRewardedAd
 * @see showRewardedAd
 */
@Deprecated(
    message = "Will be obsolete and deleted in Basic-Ads version 0.2.7",
    level = DeprecationLevel.WARNING
)
public expect class AdLoader() {
    /**
     * Submits an [AdRequest] and returns the resulting [AdRequest]
     * Loaded ads are stored privately in each instantiation of [AdLoader].
     * As long as [showInterstitialAd] is called from the same [AdLoader] instance, the ad will display.
     * @return a loaded or failed [AdRequest]
     */
    @Deprecated(
        message = "Will be obsolete and deleted in Basic-Ads version 0.2.7",
        level = DeprecationLevel.WARNING
    )
    public fun requestAd(): AdRequest

    /**
     * Requests and loads a fullscreen AdMob ad and notifies load completion via [onLoaded] lambda.
     * Loaded ads are stored privately in each instantiation of [AdLoader].
     * As long as [showInterstitialAd] is called from the same [AdLoader] instance, the ad will display.
     * @param activity Android Activity or iOS null value stored in an [Any] variable
     * @param adUnitId AdMob AdUnitId [String] used by [requestAd] to load the ad
     * @param onLoaded Lambda expression that executes when the [AdRequest] has fully loaded
     * @author rjamison@lexilabs.app
     * @see showInterstitialAd
     */
    @Deprecated(
        message = "Will be replaced by InterstitialAd.load() in Basic-Ads version 0.2.7",
        replaceWith = ReplaceWith(
            expression = "InterstitialAd(activity).load(adUnitId, onLoaded,onFailedToLoad)",
            imports = arrayOf("app.lexilabs.basic.ads.InterstitialAd")
        ),
        level = DeprecationLevel.WARNING
    )
    public fun loadInterstitialAd(
        activity: Any?,
        adUnitId: String,
        onLoaded: () -> Unit = {},
        onFailedToLoad: (Long) -> Unit = {},
    )

    /**
     * Shows a fullscreen AdMob ad and notifies when the user closes the ad via [onDismissed] lambda.
     * Loaded ads are stored privately in each instantiation of [AdLoader].
     * As long as [loadInterstitialAd] is called from the same [AdLoader] instance, the ad will display.
     * @param activity Android Activity or iOS null value stored in an [Any] variable
     * @param onDismissed Lambda expression that executes when the user closes the ad
     * @param onShown Lambda expression that executes after the ad is presented
     * @param onImpression Lambda expression that executes after the user has seen the ad
     * @param onClick Lambda expression that executes after the user clicks the ad
     * @param onFailure Lambda expression that executes after the ad fails to load or redirect
     * @author rjamison@lexilabs.app
     * @see loadInterstitialAd
     */
    @Deprecated(
        message = "Will be replaced by InterstitialAd.show() in Basic-Ads version 0.2.7",
        replaceWith = ReplaceWith(
            expression = "InterstitialAd(activity).show()",
            imports = arrayOf("app.lexilabs.basic.ads.InterstitialAd")
        ),
        level = DeprecationLevel.WARNING
    )
    public fun showInterstitialAd(
        activity: Any?,
        onDismissed: () -> Unit = {},
        onShown: () -> Unit = {},
        onImpression: () -> Unit = {},
        onClick: () -> Unit = {},
        onFailure: () -> Unit = {},
    )

    /**
     * Requests and loads a fullscreen AdMob ad and notifies load completion via [onLoaded] lambda.
     * Loaded ads are stored privately in each instantiation of [AdLoader].
     * As long as [showRewardedInterstitialAd] is called from the same [AdLoader] instance, the ad will display.
     * @param activity Android Activity or iOS null value stored in an [Any] variable
     * @param adUnitId AdMob AdUnitId [String] used by [requestAd] to load the ad
     * @param onLoaded Lambda expression that executes when the [AdRequest] has fully loaded
     * @author rjamison@lexilabs.app
     * @see showRewardedInterstitialAd
     */
    @Deprecated(
        message = "Will be replaced by RewardedInterstitialAd.load() in Basic-Ads version 0.2.7",
        replaceWith = ReplaceWith(
            expression = "RewardedInterstitialAd(activity).load(adUnitId, onLoaded,onFailedToLoad)",
            imports = arrayOf("app.lexilabs.basic.ads.RewardedInterstitialAd")
        ),
        level = DeprecationLevel.WARNING
    )
    public fun loadRewardedInterstitialAd(
        activity: Any?,
        adUnitId: String,
        onLoaded: () -> Unit = {},
        onFailedToLoad: (Long) -> Unit = {},
    )

    /**
     * Shows a rewarded and fullscreen AdMob ad.
     * It notifies when the user closes the ad via [onDismissed] lambda and
     * when the user earned a reward within the ad using lambda [onRewardEarned]
     * Loaded ads are stored privately in each instantiation of [AdLoader].
     * As long as [loadRewardedInterstitialAd] is called from the same [AdLoader] instance, the ad will display.
     * @param activity Android Activity or iOS null value stored in an [Any] variable
     * @param onDismissed Lambda expression that executes when the user closes the ad
     * @param onRewardEarned Lambda expression that executes when the user earns a reward from the ad
     * @param onShown Lambda expression that executes after the ad is presented
     * @param onImpression Lambda expression that executes after the user has seen the ad
     * @param onClick Lambda expression that executes after the user clicks the ad
     * @param onFailure Lambda expression that executes after the ad fails to load or redirect
     * @author rjamison@lexilabs.app
     * @see loadRewardedInterstitialAd
     */
    @Deprecated(
        message = "Will be replaced by RewardedInterstitialAd.show() in Basic-Ads version 0.2.7",
        replaceWith = ReplaceWith(
            expression = "InterstitialAd(activity).show(onRewardEarned)",
            imports = arrayOf("app.lexilabs.basic.ads.RewardedInterstitialAd")
        ),
        level = DeprecationLevel.WARNING
    )
    public fun showRewardedInterstitialAd(
        activity: Any?,
        onRewardEarned: () -> Unit,
        onDismissed: () -> Unit = {},
        onShown: () -> Unit = {},
        onImpression: () -> Unit = {},
        onClick: () -> Unit = {},
        onFailure: () -> Unit = {},
    )

    /**
     * Requests and loads a fullscreen AdMob ad and notifies load completion via [onLoaded] lambda.
     * Loaded ads are stored privately in each instantiation of [AdLoader].
     * As long as [showRewardedAd] is called from the same [AdLoader] instance, the ad will display.
     * @param activity Android Activity or iOS null value stored in an [Any] variable
     * @param adUnitId AdMob AdUnitId [String] used by [requestAd] to load the ad
     * @param onLoaded Lambda expression that executes when the [AdRequest] has fully loaded
     * @author rjamison@lexilabs.app
     * @see showRewardedAd
     */
    @Deprecated(
        message = "Will be replaced by RewardedAd.load() in Basic-Ads version 0.2.7",
        replaceWith = ReplaceWith(
            expression = "RewardedAd(activity).load(adUnitId, onLoaded,onFailedToLoad)",
            imports = arrayOf("app.lexilabs.basic.ads.RewardedAd")
        ),
        level = DeprecationLevel.WARNING
    )
    public fun loadRewardedAd(
        activity: Any?,
        adUnitId: String,
        onLoaded: () -> Unit = {},
        onFailedToLoad: (Long) -> Unit = {},
    )

    /**
     * Shows a rewarded and fullscreen AdMob ad.
     * It notifies when the user closes the ad via [onDismissed] lambda and
     * when the user earned a reward within the ad using lambda [onRewardEarned]
     * Loaded ads are stored privately in each instantiation of [AdLoader].
     * As long as [loadRewardedAd] is called from the same [AdLoader] instance, the ad will display.
     * @param activity Android Activity or iOS null value stored in an [Any] variable
     * @param onDismissed Lambda expression that executes when the user closes the ad
     * @param onRewardEarned Lambda expression that executes when the user earns a reward from the ad
     * @param onShown Lambda expression that executes after the ad is presented
     * @param onImpression Lambda expression that executes after the user has seen the ad
     * @param onClick Lambda expression that executes after the user clicks the ad
     * @param onFailure Lambda expression that executes after the ad fails to load or redirect
     * @author rjamison@lexilabs.app
     * @see loadRewardedAd
     */
    @Deprecated(
        message = "Will be replaced by InterstitialAd.show() in Basic-Ads version 0.2.7",
        replaceWith = ReplaceWith(
            expression = "RewardedAd(activity).show(onRewardEarned)",
            imports = arrayOf("app.lexilabs.basic.ads.RewardedAd")
        ),
        level = DeprecationLevel.WARNING
    )
    public fun showRewardedAd(
        activity: Any?,
        onRewardEarned: () -> Unit,
        onDismissed: () -> Unit = {},
        onShown: () -> Unit = {},
        onImpression: () -> Unit = {},
        onClick: () -> Unit = {},
        onFailure: () -> Unit = {},
    )
}