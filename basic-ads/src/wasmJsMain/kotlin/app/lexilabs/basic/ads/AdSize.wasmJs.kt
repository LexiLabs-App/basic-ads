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
public actual class AdSize actual constructor(width: Int, height: Int) {
    /**
     * The width of the ad in whatever unit of measurement Google came up with
     */
    public actual val width: Int = 0

    /**
     * The height of the ad in whatever unit of measurement Google came up with
     */
    public actual val height: Int = 0

    public actual companion object {
        public actual val FULL_WIDTH: Int = 0
        public actual val AUTO_HEIGHT: Int = 0
        public actual val BANNER: AdSize = AdSize(0, 0)
        public actual val FULL_BANNER: AdSize = AdSize(0, 0)
        public actual val LARGE_BANNER: AdSize = AdSize(0,0)
        public actual val LEADERBOARD: AdSize = AdSize(0,0)
        public actual val MEDIUM_RECTANGLE: AdSize = AdSize(0,0)
        public actual val WIDE_SKYSCRAPER: AdSize = AdSize(0,0)
        public actual val FLUID: AdSize = AdSize(0,0)
        public actual val INVALID: AdSize = AdSize(0,0)

        public actual fun autoSelect(
            androidAdSize: AdSize,
            iosAdSize: AdSize
        ): AdSize = AdSize(0,0)
    }

}