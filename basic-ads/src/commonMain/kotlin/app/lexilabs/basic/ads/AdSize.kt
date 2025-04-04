package app.lexilabs.basic.ads

/**
 * Based on the AdMob implementation of AdSize, this module enables Android-like syntax to be used multiplatform.
 * @param width The width of the ad in whatever unit of measurement Google came up with
 * @param height The height of the ad in whatever unit of measurement Google came up with
 * @see FULL_WIDTH
 * @see AUTO_HEIGHT
 * @see BANNER
 * @see FULL_BANNER
 * @see LARGE_BANNER
 * @see LEADERBOARD
 * @see MEDIUM_RECTANGLE
 * @see WIDE_SKYSCRAPER
 * @see FLUID
 * @see INVALID
 */
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
    }

    public fun autoSelect(androidAdSize: AdSize, iosAdSize: AdSize): AdSize
}