package app.lexilabs.basic.ads

import android.content.Context

public actual class AdSize public actual constructor(public actual val width: Int, public actual val height: Int) {

    init {
        com.google.android.gms.ads.AdSize(width, height)
    }

    public actual companion object {
        public actual val FULL_WIDTH: Int = com.google.android.gms.ads.AdSize.FULL_WIDTH
        public actual val AUTO_HEIGHT: Int = com.google.android.gms.ads.AdSize.AUTO_HEIGHT
        public actual val BANNER: AdSize = com.google.android.gms.ads.AdSize.BANNER.toCommon()
        public actual val FULL_BANNER: AdSize = com.google.android.gms.ads.AdSize.FULL_BANNER.toCommon()
        public actual val LARGE_BANNER: AdSize = com.google.android.gms.ads.AdSize.LARGE_BANNER.toCommon()
        public actual val LEADERBOARD: AdSize = com.google.android.gms.ads.AdSize.LEADERBOARD.toCommon()
        public actual val MEDIUM_RECTANGLE: AdSize = com.google.android.gms.ads.AdSize.MEDIUM_RECTANGLE.toCommon()
        public actual val WIDE_SKYSCRAPER: AdSize = com.google.android.gms.ads.AdSize.WIDE_SKYSCRAPER.toCommon()
        public actual val FLUID: AdSize = com.google.android.gms.ads.AdSize.FLUID.toCommon()
        public actual val INVALID: AdSize = com.google.android.gms.ads.AdSize.INVALID.toCommon()

        public actual fun autoSelect(androidAdSize: AdSize, iosAdSize: AdSize): AdSize = androidAdSize

        public actual fun getCurrentOrientationAnchoredAdaptiveBannerAdSize(context: Any?, width: Int): AdSize {
            require(context != null && context is Context) {
                "`getCurrentOrientationAnchoredAdaptiveBannerAdSize` requires argument `context` to be an Android `Context` type"
            }
            return com.google.android.gms.ads.AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, width).toCommon()
        }

        public actual fun getPortraitAnchoredAdaptiveBannerAdSize(context: Any?, width: Int): AdSize {
            require(context != null && context is Context) {
                "`getPortraitAnchoredAdaptiveBannerAdSize` requires argument `context` to be an Android `Context` type"
            }
            return com.google.android.gms.ads.AdSize.getPortraitAnchoredAdaptiveBannerAdSize(context, width).toCommon()
        }
        public actual fun getLandscapeAnchoredAdaptiveBannerAdSize(context: Any?, width: Int): AdSize {
            require(context != null && context is Context) {
                "`getLandscapeAnchoredAdaptiveBannerAdSize` requires argument `context` to be an Android `Context` type"
            }
            return com.google.android.gms.ads.AdSize.getLandscapeAnchoredAdaptiveBannerAdSize(context, width).toCommon()
        }

        public actual fun getCurrentOrientationInlineAdaptiveBannerAdSize(context: Any?, width: Int): AdSize {
            require(context != null && context is Context) {
                "`getCurrentOrientationInlineAdaptiveBannerAdSize` requires argument `context` to be an Android `Context` type"
            }
            return com.google.android.gms.ads.AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(context, width).toCommon()
        }

        public actual fun getPortraitInlineAdaptiveBannerAdSize(context: Any?, width: Int): AdSize {
            require(context != null && context is Context) {
                "`getPortraitInlineAdaptiveBannerAdSize` requires argument `context` to be an Android `Context` type"
            }
            return com.google.android.gms.ads.AdSize.getPortraitInlineAdaptiveBannerAdSize(context, width).toCommon()
        }

        public actual fun getLandscapeInlineAdaptiveBannerAdSize(context: Any?, width: Int): AdSize {
            require(context != null && context is Context) {
                "`getLandscapeInlineAdaptiveBannerAdSize` requires argument `context` to be an Android `Context` type"
            }
            return com.google.android.gms.ads.AdSize.getLandscapeInlineAdaptiveBannerAdSize(context, width).toCommon()
        }
    }
}
