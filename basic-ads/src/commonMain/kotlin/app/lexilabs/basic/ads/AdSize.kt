package app.lexilabs.basic.ads

/**
 * Based on the AdMob implementation of AdSize, this module enables Android-like syntax to be used multiplatform.
 * @param width The width of the ad in density-independent pixels (dp).
 * @param height The height of the ad in density-independent pixels (dp).
 * @see FULL_WIDTH A dynamic ad size that matches the width of the screen.
 * @see AUTO_HEIGHT A dynamic ad size that matches the height of the screen.
 * @see BANNER A 320x50 banner ad.
 * @see FULL_BANNER A 468x60 full banner ad.
 * @see LARGE_BANNER A 320x100 large banner ad.
 * @see LEADERBOARD A 728x90 leaderboard ad.
 * @see MEDIUM_RECTANGLE A 300x250 medium rectangle ad.
 * @see WIDE_SKYSCRAPER A 160x600 wide skyscraper ad.
 * @see FLUID A dynamically sized ad that matches its parent's width and dynamically sets its height to match the ad creative.
 * @see INVALID An invalid ad size.
 */
@Suppress("unused")
@DependsOnGoogleMobileAds
public expect class AdSize public constructor(width: Int, height: Int) {

    /**
     * The width of the ad in whatever unit of measurement Google came up with
     */
    public val width: Int

    /**
     * The height of the ad in whatever unit of measurement Google came up with
     */
    public val height: Int

    public companion object {
        public val FULL_WIDTH: Int
        public val AUTO_HEIGHT: Int
        public val BANNER: AdSize
        public val FULL_BANNER: AdSize
        public val LARGE_BANNER: AdSize
        public val LEADERBOARD: AdSize
        public val MEDIUM_RECTANGLE: AdSize
        public val WIDE_SKYSCRAPER: AdSize
        public val FLUID: AdSize
        public val INVALID: AdSize

        /**
         * Provides the appropriate [AdSize] based on the platform
         * @param androidAdSize The Android implemented [AdSize]
         * @param iosAdSize The iOS implemented [AdSize]
         */
        public fun autoSelect(androidAdSize: AdSize, iosAdSize: AdSize): AdSize

        /**
         * Gets an anchored adaptive banner ad size for the current orientation.
         * @param context Not used on iOS. Can be null.
         * @param width The width of the ad container.
         * @return The adaptive [AdSize].
         */
        public fun getCurrentOrientationAnchoredAdaptiveBannerAdSize(context: Any?, width: Int): AdSize

        /**
         * Gets an anchored adaptive banner ad size for portrait orientation.
         * @param context Not used on iOS. Can be null.
         * @param width The width of the ad container.
         * @return The adaptive [AdSize].
         */
        public fun getPortraitAnchoredAdaptiveBannerAdSize(context: Any?, width: Int): AdSize

        /**
         * Gets an anchored adaptive banner ad size for landscape orientation.
         * @param context Not used on iOS. Can be null.
         * @param width The width of the ad container.
         * @return The adaptive [AdSize].
         */
        public fun getLandscapeAnchoredAdaptiveBannerAdSize(context: Any?, width: Int): AdSize

        /**
         * Gets an inline adaptive banner ad size for the current orientation.
         * @param context Not used on iOS. Can be null.
         * @param width The width of the ad container.
         * @return The adaptive [AdSize].
         */
        public fun getCurrentOrientationInlineAdaptiveBannerAdSize(context: Any?, width: Int): AdSize

        /**
         * Gets an inline adaptive banner ad size for portrait orientation.
         * @param context Not used on iOS. Can be null.
         * @param width The width of the ad container.
         * @return The adaptive [AdSize].
         */
        public fun getPortraitInlineAdaptiveBannerAdSize(context: Any?, width: Int): AdSize

        /**
         * Gets an inline adaptive banner ad size for landscape orientation.
         * @param context Not used on iOS. Can be null.
         * @param width The width of the ad container.
         * @return The adaptive [AdSize].
         */
        public fun getLandscapeInlineAdaptiveBannerAdSize(context: Any?, width: Int): AdSize

        /**
         * Gets an inline adaptive banner ad size for any orientation.
         * @param width The width of the ad container.
         * @param maxHeight The maximum height of the ad container.
         * @return The adaptive [AdSize].
         */
        public fun getInlineAdaptiveBannerAdSize(width: Int, maxHeight: Int): AdSize
    }
}