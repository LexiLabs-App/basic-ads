package app.lexilabs.basic.ads

import android.content.Context

/**
 * Represents the size of an ad.
 *
 * @param width The width of the ad.
 * @param height The height of the ad.
 */
public actual class AdSize public actual constructor(public actual val width: Int, public actual val height: Int) {

    init {
        com.google.android.gms.ads.AdSize(width, height)
    }

    public actual companion object {
        /** A constant for full-width ads. */
        public actual val FULL_WIDTH: Int = com.google.android.gms.ads.AdSize.FULL_WIDTH
        /** A constant for auto-height ads. */
        public actual val AUTO_HEIGHT: Int = com.google.android.gms.ads.AdSize.AUTO_HEIGHT
        /** Standard banner ad size (320x50). */
        public actual val BANNER: AdSize = com.google.android.gms.ads.AdSize.BANNER.toCommon()
        /** Full banner ad size (468x60). */
        public actual val FULL_BANNER: AdSize = com.google.android.gms.ads.AdSize.FULL_BANNER.toCommon()
        /** Large banner ad size (320x100). */
        public actual val LARGE_BANNER: AdSize = com.google.android.gms.ads.AdSize.LARGE_BANNER.toCommon()
        /** Leaderboard ad size (728x90). */
        public actual val LEADERBOARD: AdSize = com.google.android.gms.ads.AdSize.LEADERBOARD.toCommon()
        /** Medium rectangle ad size (300x250). */
        public actual val MEDIUM_RECTANGLE: AdSize = com.google.android.gms.ads.AdSize.MEDIUM_RECTANGLE.toCommon()
        /** Wide skyscraper ad size (160x600). */
        public actual val WIDE_SKYSCRAPER: AdSize = com.google.android.gms.ads.AdSize.WIDE_SKYSCRAPER.toCommon()
        /** Fluid ad size. */
        public actual val FLUID: AdSize = com.google.android.gms.ads.AdSize.FLUID.toCommon()
        /** Invalid ad size. */
        public actual val INVALID: AdSize = com.google.android.gms.ads.AdSize.INVALID.toCommon()

        /**
         * Selects the appropriate ad size for the current platform.
         *
         * @param androidAdSize The ad size for Android.
         * @param iosAdSize The ad size for iOS.
         * @return The selected ad size.
         */
        public actual fun autoSelect(androidAdSize: AdSize, iosAdSize: AdSize): AdSize = androidAdSize

        /**
         * Gets the anchored adaptive banner ad size for the current orientation.
         *
         * @param context The context.
         * @param width The width of the ad.
         * @return The anchored adaptive banner ad size.
         */
        public actual fun getCurrentOrientationAnchoredAdaptiveBannerAdSize(context: Any?, width: Int): AdSize {
            require(context != null && context is Context) {
                "`getCurrentOrientationAnchoredAdaptiveBannerAdSize` requires argument `context` to be an Android `Context` type"
            }
            return com.google.android.gms.ads.AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, width).toCommon()
        }

        /**
         * Gets the portrait anchored adaptive banner ad size.
         *
         * @param context The context.
         * @param width The width of the ad.
         * @return The portrait anchored adaptive banner ad size.
         */
        public actual fun getPortraitAnchoredAdaptiveBannerAdSize(context: Any?, width: Int): AdSize {
            require(context != null && context is Context) {
                "`getPortraitAnchoredAdaptiveBannerAdSize` requires argument `context` to be an Android `Context` type"
            }
            return com.google.android.gms.ads.AdSize.getPortraitAnchoredAdaptiveBannerAdSize(context, width).toCommon()
        }
        /**
         * Gets the landscape anchored adaptive banner ad size.
         *
         * @param context The context.
         * @param width The width of the ad.
         * @return The landscape anchored adaptive banner ad size.
         */
        public actual fun getLandscapeAnchoredAdaptiveBannerAdSize(context: Any?, width: Int): AdSize {
            require(context != null && context is Context) {
                "`getLandscapeAnchoredAdaptiveBannerAdSize` requires argument `context` to be an Android `Context` type"
            }
            return com.google.android.gms.ads.AdSize.getLandscapeAnchoredAdaptiveBannerAdSize(context, width).toCommon()
        }

        /**
         * Gets the inline adaptive banner ad size for the current orientation.
         *
         * @param context The context.
         * @param width The width of the ad.
         * @return The inline adaptive banner ad size.
         */
        public actual fun getCurrentOrientationInlineAdaptiveBannerAdSize(context: Any?, width: Int): AdSize {
            require(context != null && context is Context) {
                "`getCurrentOrientationInlineAdaptiveBannerAdSize` requires argument `context` to be an Android `Context` type"
            }
            return com.google.android.gms.ads.AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(context, width).toCommon()
        }

        /**
         * Gets the portrait inline adaptive banner ad size.
         *
         * @param context The context.
         * @param width The width of the ad.
         * @return The portrait inline adaptive banner ad size.
         */
        public actual fun getPortraitInlineAdaptiveBannerAdSize(context: Any?, width: Int): AdSize {
            require(context != null && context is Context) {
                "`getPortraitInlineAdaptiveBannerAdSize` requires argument `context` to be an Android `Context` type"
            }
            return com.google.android.gms.ads.AdSize.getPortraitInlineAdaptiveBannerAdSize(context, width).toCommon()
        }

        /**
         * Gets the landscape inline adaptive banner ad size.
         *
         * @param context The context.
         * @param width The width of the ad.
         * @return The landscape inline adaptive banner ad size.
         */
        public actual fun getLandscapeInlineAdaptiveBannerAdSize(context: Any?, width: Int): AdSize {
            require(context != null && context is Context) {
                "`getLandscapeInlineAdaptiveBannerAdSize` requires argument `context` to be an Android `Context` type"
            }
            return com.google.android.gms.ads.AdSize.getLandscapeInlineAdaptiveBannerAdSize(context, width).toCommon()
        }

        /**
         * Gets the inline adaptive banner ad size.
         *
         * @param width The width of the ad.
         * @param maxHeight The maximum height of the ad.
         * @return The inline adaptive banner ad size.
         */
        public actual fun getInlineAdaptiveBannerAdSize(width: Int, maxHeight: Int): AdSize =
            com.google.android.gms.ads.AdSize.getInlineAdaptiveBannerAdSize(width, maxHeight).toCommon()
    }
}
