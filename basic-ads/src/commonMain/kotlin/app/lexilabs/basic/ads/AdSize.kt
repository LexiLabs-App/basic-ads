package app.lexilabs.basic.ads

/**
 * Represents the size of an ad in density-independent pixels (dp).
 * This is a multiplatform representation of the AdMob AdSize.
 *
 * @param width The width of the ad in density-independent pixels (dp).
 * @param height The height of the ad in density-independent pixels (dp).
 */
@Suppress("unused")
@DependsOnGoogleMobileAds
public expect class AdSize public constructor(width: Int, height: Int) {

    /** The width of the ad in density-independent pixels (dp). */
    public val width: Int

    /** The height of the ad in density-independent pixels (dp). */
    public val height: Int

    public companion object {
        /** A dynamic ad size that matches the width of the screen. */
        public val FULL_WIDTH: Int
        /** A dynamic ad size that can be used for height, indicating that the ad should determine its own height. */
        public val AUTO_HEIGHT: Int
        /** A 320x50 banner ad. */
        public val BANNER: AdSize
        /** A 468x60 full banner ad. */
        public val FULL_BANNER: AdSize
        /** A 320x100 large banner ad. */
        public val LARGE_BANNER: AdSize
        /** A 728x90 leaderboard ad. */
        public val LEADERBOARD: AdSize
        /** A 300x250 medium rectangle ad. */
        public val MEDIUM_RECTANGLE: AdSize
        /** A 160x600 wide skyscraper ad. */
        public val WIDE_SKYSCRAPER: AdSize
        /** A dynamically sized ad that matches its parent's width and dynamically sets its height to match the ad creative. */
        public val FLUID: AdSize
        /** An invalid ad size. */
        public val INVALID: AdSize

        /**
         * Provides the appropriate [AdSize] based on the platform.
         * @param androidAdSize The Android implemented [AdSize].
         * @param iosAdSize The iOS implemented [AdSize].
         * @return The ad size for the current platform.
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